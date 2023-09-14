package com.ruppyrup.qa;

import com.ruppyrup.qa.scenariodata.TestData;
import io.cucumber.spring.ScenarioScope;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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

    @LoadBalanced
    @Bean(name = "loadBalancedRestTemplate")
    public RestTemplate customRestTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "normalRestTemplate")
    public RestTemplate normalRestTemplate() {
        return new RestTemplate();
    }
}
