Feature: Handle Group

  Background:
    Given a user "Alice"
    And a user "John"
    And a user "Bob"

  Scenario: Create a new Group
    When "Alice" creates the group "Friends" with the members:
      | John |
      | Bob  |
    Then "Alice" is the owner of the group
    And the members of the group are:
      | Bob   |
      | Alice |
      | John  |
    And the group id is not null


