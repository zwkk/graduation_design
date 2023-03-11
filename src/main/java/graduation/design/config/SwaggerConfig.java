package graduation.design.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static ApiInfo DEFAULT = null;
    @Bean
    public Docket docket(){
        Contact DEFAULT_CONTACT = new Contact("zwk", "", "");
        DEFAULT = new ApiInfo("graduation_design",
                "实践教材管理平台",
                "V-1.0",
                "",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("graduation.design.controller"))
                .build();
    }




}
