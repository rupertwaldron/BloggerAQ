package com.ruppyrup.qa;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ExternalApiSteps {

    @Autowired
    private TestData testDataEnhancer;

    @Autowired
    private RestTemplate customRestTemplate;

    @Given("the url with scheme {string}, host {string}, port {int} and path {string} can be tested")
    public void theCanBeTested(String scheme, String host, Integer port, String path) {
        testDataEnhancer.setData("scheme", scheme);
        testDataEnhancer.setData("host", host);
        testDataEnhancer.setData("port", port);
        testDataEnhancer.setData("path", path);
        log.info("The url to be tested :: " + scheme + "://" + host + ":" + port + "/" + path);
    }


    @When("a GET request is sent to the url")
    public void aGETRequestIsSentToTheUrl() {
        String url = testDataEnhancer.getData("url", String.class);
        log.info("Performing get request to ::" + url);
        ResponseEntity<String> response = customRestTemplate.getForEntity(url, String.class);
        testDataEnhancer.setData("response", response);
    }


    @Then("the response {string} is received")
    public void theResponseIsReceived(String requiredResponse) {
        log.info("Checking the response is received ::" + requiredResponse);
        ResponseEntity<String> response = testDataEnhancer.getData("response", ResponseEntity.class);
        assertThat(response.getBody()).isEqualTo(requiredResponse);
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
                .scheme(testDataEnhancer.getData("scheme", String.class))
                .host(testDataEnhancer.getData("host", String.class))
                .port(testDataEnhancer.getData("port", Integer.class))
                .path(testDataEnhancer.getData("path", String.class))
                .build();

        var responseEntity = customRestTemplate.exchange(uriComponents.toString(), HttpMethod.POST, entity, String.class);
        testDataEnhancer.setData("response", responseEntity);
    }

    @Then("a response is received with status code {int}")
    public void aResponseIsReceivedWithStatusCode(int statusCode) {
        ResponseEntity<String> response = testDataEnhancer.getData("response", ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }
}
