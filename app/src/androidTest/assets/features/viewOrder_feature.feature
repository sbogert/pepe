Feature: View past orders
  @smoke
    @e2e
  Scenario Outline: User view past orders
    Given I am logged on as a drinker
    When I click my profile
    And I click view past orders button
    Then I am able to see past orders
    Examples:
      |  |
