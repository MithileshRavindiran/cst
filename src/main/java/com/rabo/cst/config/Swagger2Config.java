package com.rabo.cst.config;

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
 * Created by mravindran on 08/04/20.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.rabo.cst.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo())
                .useDefaultResponseMessages(false);
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Customer Statement Processor REST API")
                .description("Customer Statement Processor REST API")
                .contact(new Contact("Mithilesh  Sevugaperumal Ravindran", "", "mithilesh89ece@gmail.com"))
                .version("1.0.0")
                .build();
    }
}
