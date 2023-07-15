package dev.pabferir.xplit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Xplit WebAPI",
        description = "A web api to track and split shared expenses",
        version = "1.0"
))
public class SwaggerUIConfig {
}
