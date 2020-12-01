package de.dser.bespringboottest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2WebMvc
@Configuration
@Import(SpringDataRestConfiguration.class)
public class Swagger2Config {
    @Bean
    public Docket provideSwagerAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/api/.*"))
                .build()
                .apiInfo(apiEndpointInfo());
    }

    private ApiInfo apiEndpointInfo() {
        return new ApiInfoBuilder()
                .title("be-springboot-test")
                .description("show-case project to show typical cases and approaches")
                .contact(new Contact("Marko Modsching", "dser.de", "marko.modsching@dser.de"))
                .license("proprietary")
                .version("0.01")
                .build();
    }
}
