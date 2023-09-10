package com.ruppyrup.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class TranslationSteps extends SpringIntegrationTest {
    private String urlUnderTest;

    @Autowired
    private RestTemplate customRestTemplate;
    private ResponseEntity<String> responseEntity;

    @Given("the {string} can be tested")
    public void theCanBeTested(String url) {
        urlUnderTest = url;
        log.info("The url to be tested :: " + url);
    }


    @When("a GET request is sent to the url")
    public void aGETRequestIsSentToTheUrl() {
        log.info("Performing get request to ::" + urlUnderTest);
        ResponseEntity<String> response = customRestTemplate.getForEntity(urlUnderTest, String.class);
        responseEntity = response;
    }


    @Then("the response {string} is received")
    public void theResponseIsReceived(String requiredResponse) {
        log.info("Checking the response is received ::" + requiredResponse);
        assertThat(responseEntity.getBody()).isEqualTo(requiredResponse);
    }

    @When("a POST request is sent to the url with body {string}")
    public void aPOSTRequestIsSentToTheUrlWithBodyMessageHelloFromRupert(String body) {
        log.info("Performing POST request to the translation service");
        String blogMessage = "Hello Rupert's from new blog";
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        HttpEntity<String> entity = new HttpEntity<>(blogMessage, headers);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8040)
                .path("translation")
                .build();

        responseEntity = customRestTemplate.exchange(uriComponents.toString(), HttpMethod.POST, entity, String.class);
    }

    @Then("a response is received with status code {int}")
    public void aResponseIsReceivedWithStatusCode(int statusCode) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }
}
