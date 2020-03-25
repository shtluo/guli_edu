package cn.guliedu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author luoshitao
 * @Description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.guliedu"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("后台管理系统-课程中心API文档")
                .description("本文档描述了后台管理系统课程中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("Luo Shitao", "http://atguigu.com", "55317332@qq.com"))
                .build();
    }
}
