package com.ruppyrup.steps;

import com.ruppyrup.SpringBean;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@CucumberContextConfiguration
@SpringBootTest
public class CustomSteps {

    private String resultMessage;

    @Autowired
    private SpringBean springBean;


    @Given("there the bean is created")
    public void thereTheBeanIsCreated() {
        assertThat(springBean).isNotNull();
        log.info("The spring bean has been created");
    }

    @When("the message is produced")
    public void theMessageIsProduced() {
        resultMessage = springBean.printProperties();
    }

    @Then("I will receive the correct message")
    public void iWillReceiveTheCorrectMessage() {
        System.getProperties().forEach((k, v) -> System.out.println(k + " ::: " + v));
        assertThat(resultMessage).isEqualTo("chimp99");
    }
}
