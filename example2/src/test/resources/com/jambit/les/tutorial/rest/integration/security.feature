Feature: Role based security

  Scenario: Customer cannot create user
    Given I am a CUSTOMER
    When I create a user
    Then I get a FORBIDDEN response

  Scenario: Operations manager can create user
    Given I am a OPERATIONS_MANAGER
    When I create a user
    Then I get a CREATED response

#  Alternative to the previous two scenario:
  Scenario Outline: Create user access rights
    Given I am a <role>
    When I create a user
    Then I get a <httpStatus> response

    Examples:
      | role               | httpStatus |
      | CUSTOMER           | FORBIDDEN  |
      | OPERATIONS_MANAGER | CREATED    |
