package com.ruppyrup.qa;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@CucumberContextConfiguration
@SpringBootTest
public class CucumberTestContextConfiguration {
}
