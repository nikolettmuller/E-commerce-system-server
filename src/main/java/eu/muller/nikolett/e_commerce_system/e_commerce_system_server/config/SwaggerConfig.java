package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final var securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("E-commerce system server APIs")
                        .description("This is an e-commerce system web application's backend side. " +
                                "It has user management with registration and login." +
                                "Admin can manage the products, such as add, update or delete items. " +
                                "For users placing an order and checking their own orders also an " +
                                "available function. Users can also see their own details.")
                        .version("v0.0.1"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
