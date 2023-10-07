package com.ruppyrup.qa.steps;

import com.ruppyrup.qa.scenariodata.TestData;
import com.ruppyrup.qa.steps.kafka.KafkaConsumer;
import com.ruppyrup.qa.steps.kafka.KafkaProducer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class KafkaSteps {

    @Autowired
    private TestData testDataEnhancer; // brings in our scenario data sthore

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer consumer;

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;


    @Given("there is an actual embedded kafka")
    public void thereIsAnActualEmbeddedKafka() {
        assertThat(producer).isNotNull();
        assertThat(consumer).isNotNull();
    }

    @When("a message is sent of {string} is sent")
    public void aMessageIsSentOfIsSent(String message) {
        testDataEnhancer.setData("message", message);
        producer.send(message);
    }

    @Then("the same message is recieved by the consumer")
    public void theSameMessageIsRecievedByTheConsumer() {

    }
}
