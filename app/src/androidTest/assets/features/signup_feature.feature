Feature: Signup as drinker
  @smoke
    @e2e
  Scenario Outline: Signup
    Given I am on the user signup page
    And I enter valid email <email>
    And I enter valid password <password>
    And I click sign up button
    Then I am directed to the maps page
    Examples:
      | email        | password |
      | celiamarino@gmail.com | 123456 |