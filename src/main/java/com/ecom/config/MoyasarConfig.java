package com.ecom.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class MoyasarConfig {

    @Value("${moyasar.publishable-key}")
    private String publishableKey;

    @Value("${moyasar.secret-key}")
    private String secretKey;

    @Value("${moyasar.base-url}")
    private String baseUrl;
}

