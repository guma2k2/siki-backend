package com.siki.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}


	@Bean
	public RouteLocator sikiRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/api/users/**")
						.filters(f -> f.rewritePath("/api/(?<segment>.*)","/${segment}")
								.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://USER")
				).route(p -> p
						.path("/api/products/**")
						.filters(f -> f.rewritePath("/api/(?<segment>.*)","/${segment}")
								.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://PRODUCT")
				).route(p -> p
						.path("/api/medias/**")
						.filters(f -> f.rewritePath("/api/(?<segment>.*)","/${segment}")
								.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://MEDIA")
				).route(p -> p
						.path("/api/orders/**")
						.filters(f -> f.rewritePath("/api/(?<segment>.*)","/${segment}")
								.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://ORDER")
				).route(p -> p
						.path("/api/carts/**")
						.filters(f -> f.rewritePath("/api/(?<segment>.*)","/${segment}")
								.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://CART")
				)
				.build();
	}
}
