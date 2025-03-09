package com.example.bean;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class MyRestClient {

    @Bean
    public RestClient getClient() {
        // Use spring HTTP client
        return RestClient.builder()
                //.requestFactory(new HttpComponentsClientHttpRequestFactory())
                // .messageConverters(converters -> converters.add(new
                // MyCustomMessageConverter()))
                .baseUrl("https://google.com")
                .defaultUriVariables(Map.of("variable", "foo"))
                .defaultHeader("My-Header", "Foo")
                .defaultCookie("My-Cookie", "Bar")
                // .requestInterceptor(myCustomInterceptor)
                // .requestInitializer(myCustomInitializer)
                .build();

    }
}
