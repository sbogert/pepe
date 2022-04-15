Feature: Incorrect Login

  Scenario Outline: Incorrect Login
    Given I am on the user login page
    When I enter invalid username <email>
    And I enter valid password <password>
    And I click sign in button
    Then I get incorrect login notification
    Examples:
      | email        | password |
      | celiamarino@gmail.com | 123456 |