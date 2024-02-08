Feature: An existing user can create a new group and add members to it

	Scenario: Create a new group
		Given a user "Alice"
		And a user "Bob"
		And a user "John"
		When "Alice" creates the group "Friends" 
		And "Alice" adds "Bob" to the group "Friends"
		And "Alice" adds "John" to the group "Friends"
		Then "Alice" is the owner of the group
		And the members of the group "Friends" are:
			| Bob   |
			| Alice |
			| John  |
