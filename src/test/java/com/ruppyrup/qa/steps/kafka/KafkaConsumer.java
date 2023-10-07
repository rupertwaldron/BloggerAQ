package com.ruppyrup.qa.steps.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;

@Slf4j
public class KafkaConsumer {
    private final ObjectMapper mapper=new ObjectMapper();

    @KafkaListener(topics = "#{'${spring.kafka.topic.name}'.split(',')}")
    public void listen(List<String> recordBatch, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("------------------------------------------");
        log.info("Payload size= "+recordBatch.size());
        recordBatch.forEach(record -> {
            try {
                log.info("Bank model Json: " + mapper.writeValueAsString(recordBatch));
            } catch (JsonProcessingException e) {
                log.error("Exception occurred during messages processing ", e);
            }
        });
        log.info("------------------------------------------");
    }
}
