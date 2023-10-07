package com.ruppyrup.qa;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.test.context.EmbeddedKafka;

/**
 * @EnableDiscoveryClient - Used so this can find services from a Eureka service
 * @CucumberContextConfiguration - Integrates the spring context with that of cucumber
 * @SpringBootTest - Uses the spring context when running tests
 */
@EnableDiscoveryClient
@CucumberContextConfiguration
@EmbeddedKafka
@SpringBootTest
public class CucumberTestContextConfiguration {
}
