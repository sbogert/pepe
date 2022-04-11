Feature: Edit Menu

  Scenario Outline: Edit Menu
    Given I am logged on as seller
    When I edit menu
    And I enter drink name <drink>
    And I enter caffeine level <caffeine>
    And I enter price <price>
    And I press add
    Then Item is added to my menu
    Examples:
      | Latte | 2 | 5.00 |
