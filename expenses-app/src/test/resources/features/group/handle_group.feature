Feature: Handle Group

  Background:
    Given a user "Alice"
    And a user "John"
    And a user "Bob"
    And a user "Mark"
    And a group "LoL" with the owner "Bob" and members:
      | John |
      | Bob  |

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

  Scenario: Find group by id
    When looking for the group "LoL" by id
    Then the group "LoL" is returned
    And the members of the group are:
      | Bob  |
      | John |

  Scenario: Add a member to a group
    When "Mark" is added to the group
    Then the members of the group are:
      | Bob  |
      | Mark |
      | John |


