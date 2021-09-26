package com.fatimeyuk.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CustomerServiceApplication {
    @Bean
    public RestTemplate restTemplateBuilderr(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }


}
