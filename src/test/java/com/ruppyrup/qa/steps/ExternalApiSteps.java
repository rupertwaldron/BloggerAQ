package com.ruppyrup.qa.steps;

import com.ruppyrup.qa.scenariodata.TestData;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ExternalApiSteps {

    @Autowired
    private TestData testDataEnhancer; // brings in our scenario data sthore

    /** use the application context to obtain the restTemplate beans, if we autowired the restTemplate beans we would be both beans in a list
     * then have to distinguish between the two... this seemed easier.
     **/
    @Autowired
    ApplicationContext ctx;

    /**
     * This will run Before each scenario, but we can also bring the scenario in as an argument and use it to find any tags / annotations the
     * scenario has. Then if we find the @Eureka tag we set the loadbalancedResttemplate as the restTemplate for the scenario
     * @param scenario
     */
    @Before
    public void setRestTemplate(Scenario scenario) {
        boolean containsEurekaTag = scenario.getSourceTagNames().stream().anyMatch("@Eureka"::equals);
        if (containsEurekaTag) {
            testDataEnhancer.setData("restTemplate", ctx.getBean("loadBalancedRestTemplate", RestTemplate.class));
        } else {
            testDataEnhancer.setData("restTemplate", ctx.getBean("normalRestTemplate", RestTemplate.class));
        }
    }

    /**
     * Setting the url data
     * @param scheme http or https
     * @param host hostname
     * @param port port
     * @param path rest path
     */
    @Given("the url with scheme {string}, host {string}, port {int} and path {string} can be tested")
    public void theCanBeTested(String scheme, String host, Integer port, String path) {
        testDataEnhancer.setData("scheme", scheme);
        testDataEnhancer.setData("host", host);
        testDataEnhancer.setData("port", port);
        testDataEnhancer.setData("path", path);
        log.info("The url to be tested :: " + scheme + "://" + host + ":" + port + "/" + path);
    }

    /**
     * We don't use this, but is it a basic GET resquest
     */
    @When("a GET request is sent to the url")
    public void aGETRequestIsSentToTheUrl() {
        String url = testDataEnhancer.getData("url", String.class);
        log.info("Performing get request to ::" + url);
        RestTemplate restTemplate = testDataEnhancer.getData("restTemplate", RestTemplate.class); // gets data from our scenario data store
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        testDataEnhancer.setData("response", response);
    }

    /**
     * Checks the response from our request is the same as the one required in the tests
     * @param requiredResponse
     */
    @Then("the response {string} is received")
    public void theResponseIsReceived(String requiredResponse) {
        log.info("Checking the response is received ::" + requiredResponse);
        ResponseEntity<String> response = testDataEnhancer.getData("response", ResponseEntity.class);
        assertThat(response.getBody()).isEqualTo(requiredResponse);
    }

    /**
     * Post request to our url
     * @param body
     */
    @When("a POST request is sent to the url with body {string}")
    public void aPOSTRequestIsSentToTheUrlWithBodyMessageHelloFromRupert(String body) {
        log.info("Performing POST request to the translation service");
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(testDataEnhancer.getData("scheme", String.class))
                .host(testDataEnhancer.getData("host", String.class))
                .port(testDataEnhancer.getData("port", Integer.class))
                .path(testDataEnhancer.getData("path", String.class))
                .build();

        RestTemplate restTemplate = testDataEnhancer.getData("restTemplate", RestTemplate.class);
        var responseEntity = restTemplate.postForEntity(uriComponents.toString(), entity, String.class);
        testDataEnhancer.setData("response", responseEntity);
    }

    /**
     * Check the status code of the response
     * @param statusCode
     */
    @Then("a response is received with status code {int}")
    public void aResponseIsReceivedWithStatusCode(int statusCode) {
        ResponseEntity<String> response = testDataEnhancer.getData("response", ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }
}
