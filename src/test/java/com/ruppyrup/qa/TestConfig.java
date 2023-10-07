package com.ruppyrup.qa;

import com.ruppyrup.qa.scenariodata.TestData;
import com.ruppyrup.qa.steps.kafka.KafkaConsumer;
import com.ruppyrup.qa.steps.kafka.KafkaProducer;
import io.cucumber.spring.ScenarioScope;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
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

    @Bean
    public KafkaConsumer kafkaConsumer() {
        return new KafkaConsumer();
    }

    @Bean
    public KafkaProducer kafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaProducer(kafkaTemplate);
    }
}
