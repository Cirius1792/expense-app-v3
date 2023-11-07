Feature: Handle users

 Scenario: Create a new user
   Given a new user with username "Mario"
   When creating the new user 
   Then the new user is created with the username "Mario"
   And the new user is stored
    
