package pers.cc.spring.core.util.http;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.cc.spring.core.util.CommonUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http工具类
 *
 * @author chengce
 * @version 2016-07-30 13:37
 */
@Slf4j
public class HttpUtils {
  public static Map<String, List<String>> headerMaps;

  /**
   * 发起http请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param httpMethod 请求方式（GET、POST）
   * @param params     提交的数据
   * @return String
   */
  private static String httpUrlConnection(String requestUrl,
                                          HttpMethod httpMethod,
                                          String params,
                                          Map<String, String> headers) throws Exception {
    String realRequestUrl = getRealRequestUrl(requestUrl, httpMethod, params);
    StringBuilder stringBuilder = new StringBuilder();
    URL url = new URL(realRequestUrl);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setDoOutput(true);
    httpURLConnection.setDoInput(true);
    httpURLConnection.setUseCaches(false);
    // 设置请求方式（GET/POST）
    httpURLConnection.setRequestMethod(httpMethod.name());

    if (headers != null && headers.size() > 0) {
      for (Map.Entry<String, String> header : headers.entrySet()) {
        httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
      }
    }
    if (httpMethod.equals(HttpMethod.GET)) {
      httpURLConnection.connect();
    } else {
      httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      httpURLConnection.setRequestProperty("Accept", "application/json");
    }
    submitData(httpMethod, params, httpURLConnection);

    // 将返回的输入流转换成字符串
    InputStream inputStream = httpURLConnection.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    headerMaps = null;
    headerMaps = httpURLConnection.getHeaderFields();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
    }
    bufferedReader.close();
    inputStreamReader.close();
    // 释放资源
    inputStream.close();
    httpURLConnection.disconnect();
    return stringBuilder.toString();
  }

  private static void submitData(HttpMethod httpMethod, String params, HttpURLConnection httpURLConnection) throws IOException {
    // 当有数据需要提交时
    if (!httpMethod.equals(HttpMethod.GET) && CommonUtils.isNotEmpty(params)) {
      OutputStream outputStream = httpURLConnection.getOutputStream();
      // 注意编码格式，防止中文乱码
      outputStream.write(params.getBytes(StandardCharsets.UTF_8));
      outputStream.close();
    }
  }

  /**
   * 发起https请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param httpMethod 请求方式（GET、POST）
   * @param params     提交的数据
   * @return String
   */
  private static String httpsUrlConnection(String requestUrl,
                                           HttpMethod httpMethod,
                                           String params,
                                           Map<String, String> headers) throws Exception {
    String realRequestUrl = getRealRequestUrl(requestUrl, httpMethod, params);
    StringBuffer stringBuffer = new StringBuffer();
    // 创建SSLContext对象，并使用我们指定的信任管理器初始化
    TrustManager[] trustManagers = {SSLClient.createTrustManager()};
    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
    sslContext.init(null, trustManagers, new java.security.SecureRandom());
    // 从上述SSLContext对象中得到SSLSocketFactory对象
    SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

    URL url = new URL(realRequestUrl);
    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
    httpsURLConnection.setSSLSocketFactory(sslSocketFactory);

    httpsURLConnection.setDoOutput(true);
    httpsURLConnection.setDoInput(true);
    httpsURLConnection.setUseCaches(false);
    // 设置请求方式（GET/POST）
    httpsURLConnection.setRequestMethod(httpMethod.name());

    if (headers != null && headers.size() > 0) {
      for (Map.Entry<String, String> header : headers.entrySet()) {
        httpsURLConnection.setRequestProperty(header.getKey(), header.getValue());
      }
    }

    if (httpMethod.equals(HttpMethod.GET)) {
      httpsURLConnection.connect();
    }

    // 当有数据需要提交时
    submitData(httpMethod, params, httpsURLConnection);

    // 将返回的输入流转换成字符串
    InputStream inputStream = httpsURLConnection.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    headerMaps = null;
    headerMaps = httpsURLConnection.getHeaderFields();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      stringBuffer.append(line);
    }
    bufferedReader.close();
    inputStreamReader.close();
    // 释放资源
    inputStream.close();
    httpsURLConnection.disconnect();
    return stringBuffer.toString();
  }

  private static String getRealRequestUrl(String requestUrl, HttpMethod httpMethod, String params) {
    String realRequestUrl = requestUrl;
    if (httpMethod.equals(HttpMethod.GET)) {
      if (CommonUtils.isNotEmpty(params)) {
        realRequestUrl += "?" + params;
      }
    }
    return realRequestUrl;
  }

  public static InputStream httpsGetTest(String requestUrl) throws Exception {
    // 创建SSLContext对象，并使用我们指定的信任管理器初始化
    TrustManager[] trustManagers = {SSLClient.createTrustManager()};
    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
    sslContext.init(null, trustManagers, new java.security.SecureRandom());
    // 从上述SSLContext对象中得到SSLSocketFactory对象
    SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

    URL url = new URL(requestUrl);
    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
    httpsURLConnection.setSSLSocketFactory(sslSocketFactory);

    httpsURLConnection.setDoOutput(true);
    httpsURLConnection.setDoInput(true);
    httpsURLConnection.setUseCaches(false);
    // 设置请求方式（GET/POST）
    httpsURLConnection.setRequestMethod("GET");

    httpsURLConnection.connect();

    // 将返回的输入流转换成字符串
    InputStream inputStream = httpsURLConnection.getInputStream();
    inputStream.close();
    httpsURLConnection.disconnect();
    return inputStream;
  }

  /**
   * 发起http POST请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @param headers    请求头
   * @return String
   */
  public static String httpPost(String requestUrl, String params, Map<String, String> headers) throws Exception {
    return httpUrlConnection(requestUrl, HttpMethod.POST, params, headers);
  }

  /**
   * 发起http POST请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @return String
   */
  public static String httpPost(String requestUrl, String params) throws Exception {
    return httpPost(requestUrl, params, null);
  }

  /**
   * 发起http POST请求并获取结果
   *
   * @param requestUrl 请求地址
   * @return String
   */
  public static String httpPost(String requestUrl) throws Exception {
    return httpPost(requestUrl, null, null);
  }

  /**
   * 发起http GET请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @param headers    请求头
   * @return String
   */
  @Deprecated
  public static String httpGet(String requestUrl, String params, Map<String, String> headers) throws Exception {
    return httpUrlConnection(requestUrl, HttpMethod.GET, params, headers);
  }

  /**
   * 发起http GET请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @return String
   */
  public static String httpGet(String requestUrl, String params) throws Exception {
    return httpGet(requestUrl, params, null);
  }

  /**
   * 发起http GET请求并获取结果
   *
   * @param requestUrl 请求地址
   * @return String
   */
  public static String httpGet(String requestUrl) throws Exception {
    return httpGet(requestUrl, null, null);
  }

  /**
   * 发起http GET请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param headers    headers
   * @return response
   * @throws Exception 异常
   */
  public static String httpGet(String requestUrl, Map<String, String> headers) throws Exception {
    return httpUrlConnection(requestUrl, HttpMethod.GET, null, headers);
  }

  /**
   * 发起一个Https 请求
   *
   * @param requestUrl 请求地址
   * @param method     请求方式 GET or POST
   * @param params     参数（可为空）
   * @return 服务器响应String
   * @throws Exception 异常
   */
  public static String httpsRequest(String requestUrl, String method, String params) throws Exception {
    if (method.equalsIgnoreCase("GET")) {
      return httpsGet(requestUrl, params);
    } else {
      return httpsPost(requestUrl, params);
    }
  }

  /**
   * 发起https POST请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @param headers    请求头
   * @return String
   */
  public static String httpsPost(String requestUrl, String params, Map<String, String> headers) throws Exception {
    return httpsUrlConnection(requestUrl, HttpMethod.POST, params, headers);
  }

  /**
   * 发起https POST请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @return String
   */
  public static String httpsPost(String requestUrl, String params) throws Exception {
    return httpsPost(requestUrl, params, null);
  }

  /**
   * 发起https POST请求并获取结果
   *
   * @param requestUrl 请求地址
   * @return String
   */
  public static String httpsPost(String requestUrl) throws Exception {
    return httpsPost(requestUrl, null, null);
  }

  /**
   * 发起https GET请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @param headers    请求头
   * @return String
   */
  public static String httpsGet(String requestUrl, String params, Map<String, String> headers) throws Exception {
    return httpsUrlConnection(requestUrl, HttpMethod.GET, params, headers);
  }

  /**
   * 发起https GET请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param params     提交的数据
   * @return String
   */
  public static String httpsGet(String requestUrl, String params) throws Exception {
    return httpsGet(requestUrl, params, null);
  }

  /**
   * 发起https GET请求并获取结果
   *
   * @param requestUrl 请求地址
   * @return String
   */
  public static String httpsGet(String requestUrl) throws Exception {
    return httpsGet(requestUrl, null, null);
  }

  /**
   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
   * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
   * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
   * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
   * 192.168.1.100
   * 用户真实IP为： 192.168.1.110
   *
   * @param request HttpServletRequest
   * @return ip地址
   * @deprecated
   */
  public static String getIpAddressOld(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip.length() == 7) {
      ip = "127.0.0.1";
    }
    return ip;
  }

  public static String getIpAddress(HttpServletRequest request) {
    if (request == null) {
      return "127.0.0.1";
    }
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    if (ip != null && ip.contains(",")) {
      String[] ipWithMultiProxy = ip.split(",");

      for (String eachIpSegement : ipWithMultiProxy) {
        if (!"unknown".equalsIgnoreCase(eachIpSegement)) {
          ip = eachIpSegement;
          break;
        }
      }
    }
    return ip == null || ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
  }

  /**
   * 判断请求是否来自手机
   *
   * @param request 请求
   * @return 是否为手机
   */
  public static boolean isMobile(HttpServletRequest request) {
    boolean isMobile = false;
    String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
        "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
        "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
        "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
        "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
        "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
        "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
        "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
        "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
        "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
        "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
        "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
        "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
        "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
        "Googlebot-Mobile"};
    if (request.getHeader("User-Agent") != null) {
      String userAgent = request.getHeader("User-Agent").toLowerCase();
      if (CommonUtils.isEmpty(userAgent)) {
        return false;
      }
      for (String mobileAgent : mobileAgents) {
        if (userAgent.contains(mobileAgent)) {
          isMobile = true;
          break;
        }
      }
    }
    return isMobile;
  }


  /**
   * 将map转换成url
   *
   * @param map 参数map
   * @return url 字符串
   */
  public static String getUrlParamsByMap(Map<String, String> map) {
    if (map == null) {
      return "";
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
    }
    String s = stringBuilder.toString();
    if (s.endsWith("&")) {
      s = s.substring(0, s.length() - 1);
    }
    return s;
  }

  /**
   * 从对象中提取一个url可用的参数形式  formdata
   * 例如:   a=1&b=public&ccc=2
   *
   * @param object 对象
   * @return 参数字符串
   * @throws IllegalAccessException 异常
   */
  public static String getUrlParamsByObject(Object object) throws IllegalAccessException {
    Class cls = object.getClass();
    StringBuilder stringBuilder = new StringBuilder();
    for (Field field : cls.getDeclaredFields()) {
      field.setAccessible(true);
      if (CommonUtils.isNotEmpty(field.get(object))) {
        stringBuilder.append(field.getName())
            .append("=")
            .append(field.get(object))
            .append("&");
      }
    }
    return stringBuilder.substring(0, stringBuilder.length() - 1);
  }

  @Data
  public static class OsBrowser {
    private String os;

    private String browser;
  }

  /**
   * 获取操作系统和浏览器信息
   *
   * @param request request
   * @return 操作系统、浏览器
   */
  public static OsBrowser getOsAndBrowserInfo(HttpServletRequest request) {
    OsBrowser osBrowser = new OsBrowser();
    osBrowser.setOs("未知");
    osBrowser.setBrowser("未知");
    String userAgent = request.getHeader("User-Agent");
    if (CommonUtils.isNotEmpty(userAgent)) {
      String user = userAgent.toLowerCase();

      String os;
      String browser = "";

      //=================OS Info=======================
      if (userAgent.toLowerCase().contains("windows")) {
        os = "Windows";
      } else if (userAgent.toLowerCase().contains("mac")) {
        os = "Mac";
      } else if (userAgent.toLowerCase().contains("x11")) {
        os = "Unix";
      } else if (userAgent.toLowerCase().contains("android")) {
        os = "Android";
      } else if (userAgent.toLowerCase().contains("iphone")) {
        os = "IPhone";
      } else {
        os = "UnKnown, More-Info: " + userAgent;
      }
      //===============Browser===========================
      if (user.contains("edge")) {
        browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
      } else if (user.contains("msie")) {
        String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
        browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
      } else if (user.contains("safari") && user.contains("version")) {
        browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
            + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
      } else if (user.contains("opr") || user.contains("opera")) {
        if (user.contains("opera")) {
          browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
              + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr")) {
          browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
              .replace("OPR", "Opera");
        }

      } else if (user.contains("chrome")) {
        browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
      } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6")) ||
          (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) ||
          (user.contains("mozilla/4.08")) || (user.contains("mozilla/3"))) {
        browser = "Netscape-?";

      } else if (user.contains("firefox")) {
        browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
      } else if (user.contains("rv")) {
        String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
        browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
      } else {
        browser = "UnKnown, More-Info: " + userAgent;
      }

      osBrowser.setOs(os);
      osBrowser.setBrowser(browser);
    }
    return osBrowser;
  }

  /**
   * 获取 Httpservletrequest
   */
  public static HttpServletRequest getHttpServletRequest() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      return attributes.getRequest();
    } else {
      return null;
    }
  }

  /**
   * 转化参数放map
   * @param request
   * @return
   */
  public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
    Map<String, String> retMap = new HashMap<String, String>();

    Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

    for (Map.Entry<String, String[]> entry : entrySet) {
      String name = entry.getKey();
      String[] values = entry.getValue();
      int valLen = values.length;

      if (valLen == 1) {
        retMap.put(name, values[0]);
      } else if (valLen > 1) {
        StringBuilder sb = new StringBuilder();
        for (String val : values) {
          sb.append(",").append(val);
        }
        retMap.put(name, sb.substring(1));
      } else {
        retMap.put(name, "");
      }
    }

    return retMap;
  }
}
