package com.ruppyrup.qa.steps;

import com.ruppyrup.qa.scenariodata.TestData;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class KafkaSteps {
    @Autowired
    private TestData testDataEnhancer; // brings in our scenario data sthore

    @Autowired
    private Consumer<String, String> consumer;

    @Autowired
    private Producer<String, String> producer;

    @After("@Kafka")
    void close() {
        consumer.commitSync();
        consumer.unsubscribe();
        producer.flush();
        consumer.close();
        producer.close();
    }

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;

    @Given("the consumer subscribes to the test topic")
    public void thereIsAnActualEmbeddedKafka() throws Exception {
        consumer.subscribe(List.of(TOPIC_NAME));
    }

    @When("a message is sent with key: {string} and message: {string}")
    public void aMessageIsSentWithKeyAndMessage(String key, String message) throws ExecutionException, InterruptedException {
        testDataEnhancer.setData("key", key);
        testDataEnhancer.setData("message", message);
        producer.send(new ProducerRecord<>(TOPIC_NAME, key, message), (metadata, exception) -> {
        }).get();
    }

    @Then("the same message is recieved by the consumer")
    public void theSameMessageIsRecievedByTheConsumer() {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

        assertThat(records).singleElement().satisfies(singleRecord -> {
            assertThat(singleRecord.key()).isEqualTo(testDataEnhancer.getData("key", String.class));
            assertThat(singleRecord.value()).isEqualTo(testDataEnhancer.getData("message", String.class));
        });
    }
}
