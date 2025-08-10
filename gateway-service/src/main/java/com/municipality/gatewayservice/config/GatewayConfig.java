package com.municipality.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Service Routes
                .route("user-service", r -> r
                        .path("/api/auth/**", "/api/users/**")
                        .filters(f -> f
                                .rewritePath("/api/(?<segment>.*)", "/api/${segment}")
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://user-service"))
                
                // Complaint Service Routes
                .route("complaint-service", r -> r
                        .path("/api/complaints/**")
                        .filters(f -> f
                                .rewritePath("/api/(?<segment>.*)", "/api/${segment}")
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://complaint-service"))
                
                // Department Service Routes
                .route("department-service", r -> r
                        .path("/api/departments/**")
                        .filters(f -> f
                                .rewritePath("/api/(?<segment>.*)", "/api/${segment}")
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://department-service"))
                
                // Notification Service Routes
                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .filters(f -> f
                                .rewritePath("/api/(?<segment>.*)", "/api/${segment}")
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://notification-service"))
                
                .build();
    }
    
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20); // 10 requests per second, 20 burst capacity
    }
    
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("Authorization") != null ?
                exchange.getRequest().getHeaders().getFirst("Authorization") : "anonymous");
    }
}