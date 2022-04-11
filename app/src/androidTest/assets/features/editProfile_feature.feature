Feature: Edit Profile

  Scenario Outline: Edit profile
    Given I am on the maps page as a Seller
    When I click view profile
    And I click edit profile
    And enter valid email <email>
    And I click change password
    Then My changed information is displayed
    Examples:
      | email |
