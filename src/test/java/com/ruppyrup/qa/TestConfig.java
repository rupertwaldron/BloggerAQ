package com.ruppyrup.qa;

import com.ruppyrup.SpringBean;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @ScenarioScope
    @Bean(name = "testDataEnhancer")
    public TestData testDataEnhancer() {
        return new TestData();
    }

    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate();
    }
}
