package pers.cc.spring.api.wechat.service.impl;

import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.bean.WechatInformation;
import pers.cc.spring.api.wechat.enums.WechatPayTradeType;
import pers.cc.spring.api.wechat.model.pay.dto.WxRefundDTO;
import pers.cc.spring.api.wechat.model.pay.dto.WxUnifiedOrderDTO;
import pers.cc.spring.api.wechat.model.pay.param.WxJsPayParameter;
import pers.cc.spring.api.wechat.model.pay.response.WxPayMessage;
import pers.cc.spring.api.wechat.model.pay.response.WxRefundMessage;
import pers.cc.spring.api.wechat.service.WechatPayService;
import pers.cc.spring.api.wechat.service.WechatSignService;
import pers.cc.spring.api.wechat.util.WechatUtil;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.file.QRUtils;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.core.util.date.DateUtils;
import pers.cc.spring.core.util.other.MathUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * com.cc.api.wechat.service.impl
 *
 * @author chengce
 * @version 2017-10-26 13:22
 */
@Slf4j
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatPayImpl implements WechatPayService {

  @Autowired
  WechatSignService wechatSignService;

  @Autowired
  WechatInformation wechatAppBean;

  @Value("${wechat.url.pay.unifiedOrder}")
  private String payUrl;

  @Value("${wechat.url.pay.refund}")
  private String refundUrl;


  @Override
  public Message<WxJsPayParameter> JsPay(WxUnifiedOrderDTO wxUnifiedOrderDTO) {
    Message<WxPayMessage> wxPayMessageMessage = pay(wxUnifiedOrderDTO);
    Message<WxJsPayParameter> message = new Message<>();
    if (wxPayMessageMessage.isSuccess()) {
      WxPayMessage payMessage = wxPayMessageMessage.getData();

      WxJsPayParameter wxJsPayParameter = new WxJsPayParameter();
      wxJsPayParameter.setAppId(wxUnifiedOrderDTO.getAppid());
      wxJsPayParameter.setTimeStamp(String.valueOf(DateUtils.getTimestamp(TimeUnit.SECONDS)));
      wxJsPayParameter.setNonceStr(MathUtils.getRandom(16, MathUtils.NonceType.CHAR));
      wxJsPayParameter.setSignType("MD5");
      wxJsPayParameter.setPackage(payMessage.getPrepay_id());
      wxJsPayParameter.setPaySign(
          wechatSignService.createSign("UTF-8", ClassUtils.objectToSortedMap(wxJsPayParameter), wechatAppBean.getMerchantKey()));
      message.setSuccess(true);
      message.setData(wxJsPayParameter);
    } else {
      message.setMessage(wxPayMessageMessage.getMessage());
    }
    return message;
  }

  @Override
  public Message<WxPayMessage> pay(WxUnifiedOrderDTO wxUnifiedOrderDTO) {
    wxUnifiedOrderDTO.setAppid(wechatAppBean.getAppId());
    wxUnifiedOrderDTO.setMch_id(wechatAppBean.getMerchantId());
    String requestXml = wechatSignService.createSignAndXml(wxUnifiedOrderDTO, wechatAppBean.getMerchantKey());
    Message<WxPayMessage> wxPayMessageMessage = WechatUtil.httpsPay(payUrl, requestXml);
    if (wxUnifiedOrderDTO.getTrade_type().equals(WechatPayTradeType.NATIVE.name())) {
      wxPayMessageMessage.ifPresent(wxPayMessage -> {
        File file;
        String qrBase64Code = null;
        try {
          file = QRUtils.writeToFile(wxUnifiedOrderDTO.getQrWidth(), wxUnifiedOrderDTO.getQrHeight(), wxPayMessage.getCode_url());
          qrBase64Code = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(file.getPath())));
          file.delete();
        } catch (WriterException | IOException e) {
          log.error("微信支付二维码转图片过程中异常，" + e.getLocalizedMessage());
        }
        wxPayMessage.setQrBase64Code("data:image/png;base64," + qrBase64Code);
      });
    }
    return wxPayMessageMessage;
  }

  @Override
  public Message<WxRefundMessage> refund(WxRefundDTO wxRefundDTO) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
//        wxRefundDTO.setAppid(appId);
//        wxRefundDTO.setMch_id(merchantId);
//
//        Message<WxRefundMessage> message = new Message<>();
//        String requestXml = WechatSignUtil.createSignAndXml(wxRefundDTO, merchantKey);
//        if (CCUtil.isEmpty(requestXml)) {
//            message.setMessage("签名生成出错");
//            return message;
//        }
////        this.getClass().getResourceAsStream("apiclient_cert.p12");
////        File file = new File("apiclient_cert.p12");
//
//
//        File file = new File("/var/www/server/apiclient_cert.p12");
////        File file = new ClassPathResource("apiclient_cert.p12").getFile();
//        if (!file.exists()) {
//            message.setMessage("证书不存在");
//            return message;
//        }
//
//        CloseableHttpClient httpclient = null;
//        try {
//            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            try {
//                FileInputStream inputStream = new FileInputStream(file);
//                try {
//                    keyStore.load(inputStream, merchantId.toCharArray());
//                    SSLContext sslcontext;
//                    try {
//                        sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, merchantId.toCharArray()).build();
//                        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext);
//                        httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
//                        HttpPost httpPost = new HttpPost(refundUrl);
//                        httpPost.setEntity(new StringEntity(requestXml));
//
//                        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
//                            HttpEntity entity = response.getEntity();
//
//                            if (entity != null) {
//                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
//                                String text;
//                                StringBuilder resp = new StringBuilder();
//                                try {
//                                    while ((text = bufferedReader.readLine()) != null) {
//                                        resp.append(text);
//                                    }
//                                    WxRefundMessage wxRefundMessage = CCUtil.mapToObject(CCJdomXmlUtil.doXMLParse(resp.toString()), WxRefundMessage.class);
//                                    if (CCUtil.isNotEmpty(wxRefundMessage) && wxRefundMessage.getReturn_code().equals("SUCCESS")) {
//                                        if (wxRefundMessage.getResult_code().equals("SUCCESS")) {
//                                            message.setSuccess(true);
//                                        } else {
//                                            message.setMessage(wxRefundMessage.getErr_code_des());
//                                        }
//                                    } else {
//                                        message.setMessage(wxRefundMessage.getReturn_msg());
//                                    }
//                                    message.setData(wxRefundMessage);
//                                } catch (JDOMException e) {
//                                    e.printStackTrace();
//                                    throw e;
//                                }
//                            }
//                            EntityUtils.consume(entity);
//                        }
//                    } catch (KeyManagementException | UnrecoverableKeyException e) {
//                        e.printStackTrace();
//                        throw e;
//                    } finally {
//                        if (httpclient != null) {
//                            httpclient.close();
//                        }
//                    }
//                } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
//                    e.printStackTrace();
//                    throw e;
//                } finally {
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (FileNotFoundException e) {
//                message.setMessage("证书不存在");
//                return message;
//            }
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return message;
    // FIXME: 2018/4/30
    return Message.<WxRefundMessage>ok().build();
  }
}
