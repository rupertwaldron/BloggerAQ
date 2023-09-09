package com.ruppyrup.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest(classes = TestConfig.class)
public class TranslationSteps {

    private String urlUnderTest;

    private String response;

    @Autowired
    private RestTemplate customRestTemplate;

    @Given("the {string} can be tested")
    public void theCanBeTested(String url) {
        urlUnderTest = url;
        log.info("The url to be tested :: " + url);
    }


    @When("a GET request is sent to the url")
    public void aGETRequestIsSentToTheUrl() {
        log.info("Performing get request to ::" + urlUnderTest);
        ResponseEntity<String> responseEntity = customRestTemplate.getForEntity(urlUnderTest, String.class);
        response = responseEntity.getBody();
    }


    @Then("the response {string} is received")
    public void theResponseIsReceived(String requiredResponse) {
        log.info("Checking the response is received ::" + requiredResponse);
        assertThat(response).isEqualTo(requiredResponse);
    }
}
