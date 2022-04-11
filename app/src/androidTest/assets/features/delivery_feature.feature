Feature: View Delivery options

  Scenario Outline: View delivery options
    Given I am logged on as a drinker
    When I click a store
    And I click view directions
    And I click bike
    Then I get a time for delivery
    Examples:
      |  |
