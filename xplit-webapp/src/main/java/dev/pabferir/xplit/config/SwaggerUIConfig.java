package dev.pabferir.xplit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        title = "Xplit WebAPI",
        description = "A Spring WebFlux REST API to track and split shared expenses.",
        version = "v1.0"
))
public class SwaggerUIConfig {
}
