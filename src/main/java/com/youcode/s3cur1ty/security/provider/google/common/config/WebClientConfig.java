package com.youcode.s3cur1ty.security.provider.google.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfig {

    @Value("${spring.security.oauth2.resource-server.opaque-token.introspection-uri}")
    private String instrospectionUri;

    @Bean
    public WebClient userinfoClient() {
        return WebClient.builder()
                .baseUrl(instrospectionUri)
                .build();
    }

}
