Feature: Translator api tests
  This feature provides happy path tests for the translator api

  @Translation
  Scenario: can translate a message
    Given the url with scheme "http", host "localhost", port 8040 and path "translation" can be tested
    When a POST request is sent to the url with body "Hello Rupert's from new blog"
    Then a response is received with status code 200
    Then the response "Bonjour Rupert's de nouveau blog" is received