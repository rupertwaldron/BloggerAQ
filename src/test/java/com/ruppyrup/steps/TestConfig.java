package com.ruppyrup.steps;

import com.ruppyrup.SpringBean;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate();
    }
}
