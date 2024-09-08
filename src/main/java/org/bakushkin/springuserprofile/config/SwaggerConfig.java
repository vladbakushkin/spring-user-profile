package org.bakushkin.springuserprofile.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String url = "http://localhost:8080";
        String title = "Spring User Profile";
        return new OpenAPI()
                .servers(List.of(new Server().url(url)))
                .info(new Info().title(title));
    }
}
