Feature: Handle Users

  Scenario: Create a new user
    Given a new user with username "Mario"
    When the user submits a register request
    Then the user unique identifier is returned
    And the username is "Mario"

