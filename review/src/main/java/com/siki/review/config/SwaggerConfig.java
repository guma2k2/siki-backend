package com.siki.review.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Review Service API", description = "Review API documentation", version = "1.0"),
        servers = {@Server(url = "${server.servlet.context-path}", description = "Default Server URL")})
public class SwaggerConfig {
}
