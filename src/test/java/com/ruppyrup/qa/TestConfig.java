package com.ruppyrup.qa;

import com.ruppyrup.qa.scenariodata.TestData;
import io.cucumber.spring.ScenarioScope;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
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

    // Need scenario scope because consumer doesn't allow multi-threaded access
    @ScenarioScope
    @Bean
    public Consumer<String, String> kafkaConsumer(ConsumerFactory<String, String> consumerFactory) {
        return consumerFactory.createConsumer("consumer", null);
    }

    @Bean
    public Producer<String, String> kafkaProducer(ProducerFactory<String, String> producerFactory) {
        return producerFactory.createProducer();
    }
}
