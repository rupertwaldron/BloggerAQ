@Kafka
Feature: Embedded kafka tests
  This feature provides happy path tests for embedded kafka

  Scenario: can send and receive on kafka
    Given the consumer subscribes to the test "topic1"
    When a message is sent with key: "key" and message: "message1" to "topic1"
    Then the same message is received by the consumer on "topic1"

  Scenario: can send and receive on kafka in parallel
    Given the consumer subscribes to the test "topic1"
    When a message is sent with key: "uuid" and message: "message2" to "topic1"
    Then the same message is received by the consumer on "topic1"