package pers.cc.spring.api.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pers.cc.spring.core.scanner.Scan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * swaggerApi注入类
 *
 * @author chengce
 * @version 2017-11-20 22:51
 */
//@ComponentScan(basePackages = {
//    Scan.PACKAGE_CC_SPRING_SECURITY_JWT,
//    Scan.PACKAGE_CC_SPRING_API_CORE
//})
//@EnableSwagger2
//@EnableWebMvc
//@EnableConfigurationProperties(ApiCoreProperties.class)
public class ApiCoreConfiguration extends WebMvcConfigurationSupport {
//public class ApiCoreConfiguration {

//    @Value("${swagger.package}")
//    private String packagePath;

  @Resource
  ApiCoreProperties apiCoreProperties;

  /**
   * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
   * <!--swagger资源配置-->
   * <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
   * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
   * 不知道为什么，这也是spring boot的一个缺点（菜鸟觉得的）
   *
   * @param registry 注册
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/static/");
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    super.addResourceHandlers(registry);
  }

//  @Bean
//  public Docket createRestApi() {
//    return new Docket(DocumentationType.SWAGGER_2)
//        .apiInfo(apiInfo())
//        .select()
//        .apis(RequestHandlerSelectors.basePackage(apiCoreProperties.getPackagePath()))
//        .paths(PathSelectors.any())
//        .build();
//  }
//
//
//  private ApiInfo apiInfo() {
//    return new ApiInfoBuilder()
//        .title("api")
//        .description("核心api接口列表")
//        .contact(new Contact("cc", "https://www.proginn.com/wo/117299.html", "351312239@qq.com"))
//        .version("1.0")
//        .build();
//  }

  @Bean
  public HttpMessageConverter<String> responseBodyConverter() {
    return new StringHttpMessageConverter(StandardCharsets.UTF_8);
  }

  @Override
  public void configureMessageConverters(
      List<HttpMessageConverter<?>> converters) {
    converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    super.configureMessageConverters(converters);
  }

  @Override
  public void configureContentNegotiation(
      ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false);
  }
}
