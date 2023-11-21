Feature: Handle Group, which includes adding and removing members as well as adding new expenses to the group

  Background:
    Given a user "Alice"
    And a user "John"
    And a user "Bob"
    And a user "Mark"
    And a group "LoL" with the owner "Bob" and members:
      | John |
      | Bob  |
    And a group "Friends" with the owner "Bob" and members:
      | John |
      | Bob  |
      | Mark |


  Scenario: Create a new Group
    When "Alice" creates the group "Friends" with the members:
      | John |
      | Bob  |
    Then "Alice" is the owner of the group
    And the members of the group "Friends" are:
      | Bob   |
      | Alice |
      | John  |
    And the group id is not null

  Scenario: Find group by id
    When looking for the group "LoL" by id
    Then the group "LoL" is returned
    And the members of the group "LoL" are:
      | Bob  |
      | John |

  Scenario: Add a member to a group
    Given the group "Friends"
    When "Mark" is added to the group
    Then the members of the group "Friends" are:
      | Bob  |
      | Mark |
      | John |


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

  Scenario Outline: Retrieve debts with a group member
    Given the group "Friends" having the expenses
      | description | owner | amount | groupId |
      | Beer        | Alice | 12.00  | Friends |
      | Wine        | Bob   | 15.00  | Friends |
    When "John" retrieves his debt to <Owner>
    Then The debt is <Debt>
    Examples:
      | Owner   | Debt   |
      | "Alice" | "4.00" |
      | "Mark"  | "0.0"  |
      | "Bob"   | "5.00" |


  Scenario: Pay a group of expenses
    Given the group "LoL" having the expenses
      | description | owner | amount | groupId |
      | Milk        | Mark  | 2.99   | Friends |
    And "John" retrieves his debt to "Mark"
    When "John" pays its debt to "Mark"
    And "John" retrieves his debt to "Mark"
    Then The debt is "0.00"

