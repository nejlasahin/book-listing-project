package com.nejlasahin.booklistingproject.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class OpenApiConfig {

    @Value("${application.title}")
    private String APP_TITLE;

    @Value("${application.version}")
    private String APP_VERSION;

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";
        final String apiTitle = String.format("%s API", StringUtils.capitalize(APP_TITLE));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .description("Please insert token below (Don't put `Bearer` before the token)")
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(
                        new Info()
                                .title(apiTitle)
                                .version(APP_VERSION)
                                .description("This is a rest api for a " + APP_TITLE + " .")
                                .termsOfService("Terms of service")
                                .contact(new Contact().email("nejlasahin177@gmail.com").name("Nejla Sahin").url("https://github.com/nejlasahin"))
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
