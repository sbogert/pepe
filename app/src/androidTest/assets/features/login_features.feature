Feature: Enter login details

  Scenario Outline: Successful login
    Given I am on the user login page
    When I enter valid email <email>
    And I enter valid password <password>
    And I click sign in button
    Then I am directed to maps page
    Examples:
      | email        | password |
      | celiamarino@gmail.com | 123456 |