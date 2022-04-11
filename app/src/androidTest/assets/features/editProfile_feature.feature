Feature: Edit Profile
  @smoke
    @e2e
  Scenario Outline: Edit profile
    Given I am on the maps page
    When I click view profile
    And I click
    And I close the keyboard
    And I click password field
    And I enter valid password <password>
    And I close the keyboard
    And I click sign in button
    Then I go to the maps page
    Examples:
      | email        | password |
      | celiamarino@gmail.com | 123456 |