@UpdateBookings
Feature: This suite includes bookings updates tests

	Background: Create Auth token
    Given user has access to endpoint "/auth"
    When user creates a auth token with credential "admin" & "password123"
    Then user should get the response code 200
    
	@Smoke @Regression
  Scenario Outline: Verify The Updation Of A Booking
    Given user has access to endpoint "/booking"
    When user makes a request to view details of a booking ID
    And user updates the details of a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin   | checkout   | additionalneeds   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then user should get the response code 200
    And user validates the response with JSON schema "bookingDetailsSchema.json"

    Examples: 
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Apiuser1      | Demo1    |      10000 | false        | 2020-01-01 | 2020-01-10 | Lunch       |
    
  @Smoke @Regression
  Scenario: Verify Partial Updates To Bookings
    Given user has access to endpoint "/booking"
    When user makes a request to view details of a booking ID
    And user makes a request to update first name "Apiuser" & Last name "Demo"
    Then user should get the response code 200
    And user validates the response with JSON schema "bookingDetailsSchema.json"