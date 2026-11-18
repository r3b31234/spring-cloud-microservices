package com.luis.springcloud.msvcitems.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration 
public class WebClientConfig {

    @Value("${config.base-url.endpoint.msvc-products}") // Inyectamos la URL base del microservicio de productos desde el archivo de propiedades
    private String url;


    @Bean
    @LoadBalanced //Para balancear la carga entre los microservicios
    WebClient.Builder webClient(){
        return WebClient.builder().baseUrl(url);
    }

}
