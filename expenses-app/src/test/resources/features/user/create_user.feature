Feature: Handle Users

  Scenario: Create a new user with a provided id
    Given the user types the id "MarioRossi"
    And the user types the password "qwerty12"
    When the user submits a register request
    Then the username is "MarioRossi"
