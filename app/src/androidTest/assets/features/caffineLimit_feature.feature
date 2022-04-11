Feature: Caffeine limit reached

  Scenario Outline: Caffeine limit
    Given I am on the menu page for <drinker>
    When I click drink
    And I click order
    And I get a caffeine limit reached notification
    Then I click dismiss
    Examples:
      | drinker |