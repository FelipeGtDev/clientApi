package com.lima.clientApi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    private ApiInfo getApiInfo() {
        ApiInfo build = new ApiInfoBuilder()
                .title("Client API")
                .description("API para cadastro de clientes e contatos")
                .version("1.0.0")
                .contact(new Contact("Felipe Lima"
                        , "https://www.linkedin.com/in/felipe-lima-gt-dev/"
                        , "noulbg@gmail.com"))
                .build();
        return  build;
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lima.clientApi.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
