Feature: create and search expenses

  Background:
    Given a user "Alice"
    And a user "John"
    And a user "Bob"
    Given a group "Friends" with the owner "Bob" and members:
      | John  |
      | Alice |

  Scenario: Create a new Expense
    Given the group "Friends"
    When "Alice" adds the expenses:
      | description | owner | amount |
      | Milk        | Alice | 2.99   |
      | Water       | Alice | 0.50   |
    Then the new expense is created
    And the new expense has a unique id

  Scenario: Find expense by id
    Given the expense:
      | id    | description | owner | amount | groupId |
      | exp-1 | Milk        | Alice | 2.99   | Friends |
    When looking for the expense "exp-1"
    Then the expense is returned:
      | id    | description | owner | amount | groupId |
      | exp-1 | Milk        | Alice | 2.99   | Friends |
