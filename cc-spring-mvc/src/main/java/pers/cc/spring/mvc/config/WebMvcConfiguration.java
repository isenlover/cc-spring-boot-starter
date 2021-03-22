package pers.cc.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 主要用于WebAsyncTask 异步请求的线程池配置
 *
 * @author chengce
 * @version 2018-04-30 14:18
 * @link <a href="https://www.jianshu.com/p/21ff7a329a3e"></a>
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

//  @Override
//  public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
//    configurer.setDefaultTimeout(60 * 1000L);
//    configurer.registerCallableInterceptors(timeoutInterceptor());
//    configurer.setTaskExecutor(threadPoolTaskExecutor());
//  }
//
//  @Bean
//  public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
//    return new TimeoutCallableProcessingInterceptor();
//  }
//
//  @Bean
//  public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//    ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
//    t.setCorePoolSize(8);
//    t.setMaxPoolSize(24);
//    t.setThreadNamePrefix("CcThreadPool");
//    return t;
//  }
//
//  @Bean
//  public HttpMessageConverter<String> responseBodyConverter() {
//    return new StringHttpMessageConverter(StandardCharsets.UTF_8);
//  }

//  @Override
//  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//    // 解决controller返回字符串中文乱码问题
//    for (HttpMessageConverter<?> converter : converters) {
//      if (converter instanceof StringHttpMessageConverter) {
//        ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
//      }
//    }
//  }

  @Bean
  public HttpMessageConverter<String> responseBodyStringConverter() {
    StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
    return converter;
  }

  /**
   * @link https://www.codenong.com/cs105731012/
   * 修改StringHttpMessageConverter默认配置
   * <p>
   * FIXME, 不能重写configureMessageConverters，否则不会设置其他默认的Converters，比如导致下载就不可用
   */
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    List<StringHttpMessageConverter> stringHttpMessageConverters = converters.stream()
        .filter(converter -> converter.getClass().equals(StringHttpMessageConverter.class))
        .map(converter -> (StringHttpMessageConverter) converter)
        .collect(Collectors.toList());

    if (stringHttpMessageConverters.isEmpty()) {
      converters.add(responseBodyStringConverter());
    } else {
      stringHttpMessageConverters.forEach(converter -> converter.setDefaultCharset(StandardCharsets.UTF_8));
    }
  }

  /**
   * 已过时
   *
   * @param converters
   * @deprecated https://www.jianshu.com/p/1f7abf8b2c64
   */
//  @Override
//  @Deprecated
//  public void configureMessageConverters(
//      List<HttpMessageConverter<?>> converters) {
//    converters.add(responseBodyConverter());
//    super.configureMessageConverters(converters);
//  }
//
//  @Override
//  @Deprecated
//  public void configureContentNegotiation(
//      ContentNegotiationConfigurer configurer) {
//    configurer.favorPathExtension(false);
//  }

  /**
   * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
   * <!--swagger资源配置-->
   * <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
   * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
   * 不知道为什么，这也是spring boot的一个缺点（菜鸟觉得的）
   *
   * @param registry 注册
   */
//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/**")
//        .addResourceLocations("classpath:/static/");
//    registry.addResourceHandler("swagger-ui.html")
//        .addResourceLocations("classpath:/META-INF/resources/");
//    registry.addResourceHandler("/webjars/**")
//        .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    super.addResourceHandlers(registry);
//  }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true).maxAge(3600);
//    }
}
