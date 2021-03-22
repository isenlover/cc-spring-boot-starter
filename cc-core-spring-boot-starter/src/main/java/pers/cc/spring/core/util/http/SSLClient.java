package pers.cc.spring.core.util.http;


import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * http 证书工具类
 *
 * @author chengce
 * @version 2016-07-28 11:12
 */
// FIXME: 2018/4/30 SSLClient
public class SSLClient {

//    public static CloseableHttpClient createSSLClient(){
//        try {
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                //信任所有
//                public boolean isTrusted(X509Certificate[] chain,
//                                         String authType) throws CertificateException {
//                    return true;
//                }
//            }).build();
//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
//            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        return  HttpClients.createDefault();
//    }

    public static TrustManager createTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
    }
}
