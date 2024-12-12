@ViewBookings 
Feature: This suite includes getting bookings tests
  @Smoke @Regression
  Scenario: Get All Bookings
    Given user has access to endpoint "/booking"
    When user makes a request to view booking IDs
    Then user should get the response code 200
    And user should see all the booking IDs
    
  @Smoke @Regression
  Scenario: Get Booking Details by Id
    Given user has access to endpoint "/booking"
    When user makes a request to view details of a booking ID
    Then user should get the response code 200
    And user validates the response with JSON schema "bookingDetailsSchema.json"
  
  @Regression   
	Scenario: Get All Bookings By First Name
    Given user has access to endpoint "/booking"
    When user makes a request to view all the booking IDs by first name
    And user should get the response code 200
    And user should see all the booking IDs
    
  @Regression   
	Scenario: Get All Bookings By Last Name
    Given user has access to endpoint "/booking"
    When user makes a request to view all the booking IDs by last name
    And user should get the response code 200
    And user should see all the booking IDs
    
  @Regression
	Scenario: Get All Bookings By First Name and Last Name
    Given user has access to endpoint "/booking"
    When user makes a request to view all the booking IDs by name
    And user should get the response code 200
    And user should see all the booking IDs
    
  @Regression
  Scenario Outline: To view all the booking IDs by booking dates
    Given user has access to endpoint "/booking"
    When user makes a request to view booking IDs from "<checkin>" to "<checkout>"
    Then user should get the response code 200
    And user should see all the booking IDs

    Examples: 
      | checkin    | checkout   |
      | 2019-04-13 | 2019-04-20 |
      | 2018-01-01 | 2019-01-01 |
      
   @Negative @Regression
   Scenario: Get Non Available Booking Details by Id
    Given user has access to endpoint "/booking"
    When user makes a request to view details for a non existent booking ID
    Then user should get the response code 404
