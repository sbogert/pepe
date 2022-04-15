Feature: Enter login details

  Scenario Outline: Successful login
    Given I am on the user login page
    When I enter valid username <username>
    And I enter valid password <password>
    And I click sign in button
    Then I am directed to maps page
    Examples:
      | username        | password |
      | celiamarino@gmail.com | 123456 |