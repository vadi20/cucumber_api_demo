@CreateBookings
Feature: This suite includes bookings creations tests
  @Smoke @Regression
  Scenario Outline: Create a New Booking
    Given user has access to endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   |  additionalneeds   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> |  <additionalneeds> |
    Then user should get the response code 200
    And user validates the response with JSON schema "createBookingSchema.json"

    Examples: 
      | firstname | lastname | totalprice | depositpaid | additionalneeds |
      | Apiuser   | Demo     |       1200 | true        | Breakfast       |

  @Smoke @Regression
  Scenario: Verify New Bookings Created By Id
    Given user has access to endpoint "/booking"
    When user makes a request to view details of a new booking ID
    Then user should get the response code 200
    
  @Smoke @Regression
  Scenario: Verify New Bookings Created By Name
    Given user has access to endpoint "/booking"
    When user makes a request to view details of a new booking ID by Name
    | firstname   | lastname   |
    | <firstname> | <lastname> |
    Then user should get the response code 200
    Examples: 
      | firstname | lastname |
      | Apiuser   | Demo     |