Feature: Order Drink

  Scenario Outline: Order Drink
    Given I am logged on as a drinker
    When I click <seller> store
    And I click menu
    And I click drink
    And I click order
    Then I am directed to order in progress page
    Examples:
      | seller |
