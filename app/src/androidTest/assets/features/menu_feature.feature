Feature: Enter login details

  Outline: Successful login
    Given I start the application
    When I click email field
    And I enter valid email <email>
    And I close the keyboard
    And I click password field
    And I enter valid password <password>
    And I close the keyboard
    And I click sign in button
    Then I go to the maps page
    Examples:
      | email        | password |
      | celiamarino@gmail.com | 123456 |