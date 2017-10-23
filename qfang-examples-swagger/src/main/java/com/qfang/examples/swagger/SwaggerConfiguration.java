package com.qfang.examples.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author huxianyong
 * @date 2017/10/20
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/stat/.*"))
                .build();
    }

    private ApiInfo apiInfo() {

        Contact contact=new Contact("1","http://www.baidu.com","qqqqqq@163.com");
        ApiInfo apiInfo = new ApiInfoBuilder().license("Apache License Version 2.0").title("Swagger 集成测试").description("Swagger API Teste").contact(contact).version("1.0").build();

        return apiInfo;
    }



}
