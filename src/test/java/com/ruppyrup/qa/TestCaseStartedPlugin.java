package com.ruppyrup.qa;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseStarted;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCaseStartedPlugin implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(
                TestCaseStarted.class,
                (TestCaseStarted event) -> log.info("Test case started with event listener : " + event.getTestCase().getName())
        );
    }
}
