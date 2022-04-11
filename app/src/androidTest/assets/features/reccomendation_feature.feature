Feature: Get recommendation
  @smoke
    @e2e
  Scenario Outline: Get recommendation
    Given I am logged on as a user
    When I click email field
    And I get recommendation notification
    Then I press dismiss
    Examples:
      |  |