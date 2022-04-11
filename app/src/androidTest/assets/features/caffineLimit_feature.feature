Feature: Caffine limit reached
  @smoke
    @e2e
  Scenario Outline: Caffine limit
    Given I am on the menu page for <drinker>
    When I click drink
    And I click drive
    And I click order
    Then I get a caffine limit reached notification
    Examples:
      | drinker |