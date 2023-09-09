Feature: Translator api tests
  This feature provides happy path tests for the translator api

  Scenario: can translate a message
    Given the "https://www.google.co.uk" can be tested
    When a GET request is sent to the url
    Then the response "testResponse" is received