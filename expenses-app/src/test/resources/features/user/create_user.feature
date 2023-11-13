Feature: Handle Users

  Scenario: Create a new user with a generated id
    Given the user type the username "Mario"
    When the user submits a register request
    Then the user unique identifier is returned
    And the username is "Mario"

  Scenario: Create a new user with a provided id
    Given the user type the username "Mario"
    And the user type the id "1234"
    When the user submits a register request
    Then the user unique identifier is "1234"
    And the username is "Mario"
