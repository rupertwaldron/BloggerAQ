@Kafka
Feature: Embedded kafka tests
  This feature provides happy path tests for embedded kafka

  Scenario: can send and receive on kafka
    Given the consumer subscribes to the test topic
    When a message is sent with key: "key" and message: "message"
    Then the same message is recieved by the consumer