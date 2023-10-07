package com.ruppyrup.qa.steps;

import com.ruppyrup.qa.scenariodata.TestData;
import com.ruppyrup.qa.steps.kafka.KafkaConsumer;
import com.ruppyrup.qa.steps.kafka.KafkaProducer;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class KafkaSteps {

    @Autowired
    private TestData testDataEnhancer; // brings in our scenario data sthore

//    @Autowired
//    private KafkaProducer producer;
//
//    @Autowired
//    private KafkaConsumer consumer;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Autowired
    private ProducerFactory<String, String> producerFactory;


    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;


    @Given("there is an actual embedded kafka")
    public void thereIsAnActualEmbeddedKafka() throws Exception {
        try (Consumer<String, String> consumer = consumerFactory.createConsumer();
             Producer<String, String> producer = producerFactory.createProducer();) {
            consumer.subscribe(List.of(TOPIC_NAME));

            producer.send(new ProducerRecord<>(TOPIC_NAME, "key", "Rupert was here"), (metadata, exception) -> {
            }).get();

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

            assertThat(records).singleElement().satisfies(singleRecord -> {
                assertThat(singleRecord.key()).isEqualTo("key");
                assertThat(singleRecord.value()).isEqualTo("Rupert was here");
            });
            consumer.commitSync();
            consumer.unsubscribe();
        }
    }

    @When("a message is sent of {string} is sent")
    public void aMessageIsSentOfIsSent(String message) {
        testDataEnhancer.setData("message", message);
//        producer.send(message);
    }

    @Then("the same message is recieved by the consumer")
    public void theSameMessageIsRecievedByTheConsumer() {
//        consumer.listen();
    }
}
