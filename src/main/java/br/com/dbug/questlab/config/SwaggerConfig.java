package br.com.dbug.questlab.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI questLabOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QuestLab API")
                        .description("API REST para gerenciamento de questões de concursos públicos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Seu Nome")
                                .email("seu.email@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
