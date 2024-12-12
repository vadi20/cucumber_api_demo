@DeleteBookings
Feature: This suite includes bookings deletion tests

  Background: create an auth token
    Given user has access to endpoint "/auth"
    When user creates a auth token with credential "admin" & "password123"
    Then user should get the response code 200

  @Smoke @Regression
  Scenario Outline: Verify Deletion of Booking
    Given user has access to endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | additionalneeds   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <additionalneeds> |
    Then user should get the response code 200
    And user validates the response with JSON schema "createBookingSchema.json"
    And user makes a request to view details of a booking ID
    And user should get the response code 200
    And user validates the response with JSON schema "bookingDetailsSchema.json"
    And user makes a request to delete booking
    And user should get the response code 201

    Examples: 
      | firstname | lastname | totalprice | depositpaid |  additionalneeds |
      | ApiUser      | Demo      |       1200 | true        |  Breakfast       |
