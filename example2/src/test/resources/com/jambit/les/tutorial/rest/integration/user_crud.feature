Feature: CRUD on users

  Background:
    Given I am a OPERATIONS_MANAGER

  Scenario: Create user
    When I create a user
    Then I get a CREATED response

  Scenario: Delete user
    Given there exists a user
    When I delete the user
    Then I get a OK response

  Scenario: Update user
    Given there exists a user
    When I update the users's address
    Then I get a OK response

  Scenario: Update non existing user
    Given there exists a user
    When I update the address of a non-existent user
    Then I get a NOT_FOUND response



