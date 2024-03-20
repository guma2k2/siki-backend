package com.siki.product.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Product Service API", description = "Product API documentation", version = "1.0"),
        servers = {@Server(url = "${server.servlet.context-path}", description = "Default Server URL")})
public class SwaggerConfig {
}
