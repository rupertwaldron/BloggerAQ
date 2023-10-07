@Kafka
Feature: Embedded kafka tests
  This feature provides happy path tests for embedded kafka

  Scenario: can send and receive on kafka
    Given there is an actual embedded kafka
    When a message is sent of "Hello Rupert's from new blog" is sent
    Then the same message is recieved by the consumer