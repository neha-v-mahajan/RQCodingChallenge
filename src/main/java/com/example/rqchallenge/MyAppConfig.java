package com.example.rqchallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyAppConfig {

    @Value("${spring.rest.template.connect.timeout}")
    private int connectTimeout;

    @Value("${spring.rest.template.read.timeout}")
    private int readTimeout;

    @Value("${spring.rest.template.max.connections}")
    private int maxConnections;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }
}
