package com.cucumber.demo.api.stepdefinition;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static com.cucumber.demo.api.utils.Utils.createBookingTempFilewithId;
import static com.cucumber.demo.api.utils.Utils.retreiveBookingIdFromTempFile;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cucumber.demo.api.data.BookingDetails;
import com.cucumber.demo.api.data.BookingId;
import com.cucumber.demo.api.utils.RestAssuredResponseHandler;
import com.cucumber.demo.api.utils.TestContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ViewBookingsTestsStepDefinition {

	private static final Logger logger = LogManager.getLogger(ViewBookingsTestsStepDefinition.class);
	private TestContext context;
	//private static BookingDetails bookingDetails;
	//private static List<Integer>  allBookingIds ;
	
	
	public  ViewBookingsTestsStepDefinition(TestContext context) {
		this.context = context;
	}
	
	@Given("user has access to endpoint {string}")
	public void userHasAccessToEndpoint(String endpoint) {		
		context.session.put("endpoint", endpoint);
	}

	@When("user makes a request to view booking IDs")
	public void userMakesARequestToViewBookingIDs() {
		context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());
		List<Integer> allBookingIds = context.response.getBody().jsonPath().getList("bookingid");
		int bookingID = allBookingIds.get(0);
		logger.debug("Booking ID: "+bookingID);
		assertNotNull(bookingID,"Booking ID not found!");
		createBookingTempFilewithId(String.valueOf(bookingID));
		
	}


	@Then("user should get the response code {int}")
	public void userShpuldGetTheResponseCode(int statusCode) {
		assertEquals(statusCode, context.response.getStatusCode());
	}

	@Then("user should see all the booking IDs")
	public void userShouldSeeAllTheBookingIDS() {		
		BookingId[] bookingIDs = RestAssuredResponseHandler.deserializedResponse(context.response, BookingId[].class);
		assertNotNull(bookingIDs,"Booking ID not found!!");		
	}
	
	@When("user makes a request to view details of a booking ID")
	public void userMakesARequestToViewDetailsOfBookingID() {
		
		int bookingID = retreiveBookingIdFromTempFile();
		
		if(bookingID == -1) {
			userMakesARequestToViewBookingIDs();
			bookingID = retreiveBookingIdFromTempFile();
		}
		
		BookingDetails bookingDetails = getBookingDetails(bookingID);
		assertNotNull(bookingDetails,"Booking Details not found!!");

	}
	
	
	public BookingDetails getBookingDetails(int bookingID) {
		context.session.put("bookingID", bookingID);
		logger.debug("Session BookingID: "+context.session.get("bookingID"));
		context.response = context.requestSetup().pathParam("bookingID", context.session.get("bookingID"))
				.when().get(context.session.get("endpoint")+"/{bookingID}");
		BookingDetails bookingDetails = RestAssuredResponseHandler.deserializedResponse(context.response, BookingDetails.class);
		return bookingDetails;
	}
	
	@When("user makes a request to view details for a non existent booking ID")
	public void getBookingDetails() {
		context.session.put("bookingID", 0);
		logger.debug("Session BookingID: "+context.session.get("bookingID"));
		context.response = context.requestSetup().pathParam("bookingID", context.session.get("bookingID"))
				.when().get(context.session.get("endpoint")+"/{bookingID}");
		BookingDetails bookingDetails = RestAssuredResponseHandler.deserializedResponse(context.response, BookingDetails.class);
		assertNull(bookingDetails,"Booking Details not found!!");

		
	}
	@Then("user validates the response with JSON schema {string}")
	public void userValidatesResponseWithJSONSchema(String schemaFileName) {
		context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/"+schemaFileName));
		logger.debug("Successfully Validated schema from "+schemaFileName);
	}
	
	@When("user makes a request to view all the booking IDs by first name")
	public void userMakesARequestToViewBookingIDByFirstName() {
		
		int bookingID = retreiveBookingIdFromTempFile();
		
		if(bookingID == -1) {
			userMakesARequestToViewBookingIDs();
			bookingID = retreiveBookingIdFromTempFile();

		}
		BookingDetails bookingDetails   = getBookingDetails(bookingID);

		context.session.put("firstname", bookingDetails.getFirstname());
		logger.debug("Session firstname: "+context.session.get("firstname"));
		context.response = context.requestSetup()
				.queryParams("firstname", context.session.get("firstname"))
				.when().get(context.session.get("endpoint").toString());	
		BookingId[] bookingIDs = RestAssuredResponseHandler.deserializedResponse(context.response, BookingId[].class);
		assertNotNull(bookingIDs,"Booking ID not found!!");
	}
	
	@When("user makes a request to view all the booking IDs by last name")
	public void userMakesARequestToViewBookingIDByLastName() {
		
		int bookingID = retreiveBookingIdFromTempFile();
		
		if(bookingID == -1) {
			userMakesARequestToViewBookingIDs();
			bookingID = retreiveBookingIdFromTempFile();

		}
		BookingDetails bookingDetails   = getBookingDetails(bookingID);
		context.session.put("lastname", bookingDetails.getLastname());
		logger.debug("Session lastname: "+context.session.get("lastname"));
		context.response = context.requestSetup()
				.queryParams("lastname", context.session.get("lastname"))
				.when().get(context.session.get("endpoint").toString());	
		BookingId[] bookingIDs = RestAssuredResponseHandler.deserializedResponse(context.response, BookingId[].class);
		assertNotNull(bookingIDs,"Booking ID not found!!");
	}
	
	@When("user makes a request to view all the booking IDs by name")
	public void userMakesARequestToViewBookingIDByUserName() {
		
		int bookingID = retreiveBookingIdFromTempFile();
		
		if(bookingID == -1) {
			userMakesARequestToViewBookingIDs();
			bookingID = retreiveBookingIdFromTempFile();

		}
		BookingDetails bookingDetails   = getBookingDetails(bookingID);
		context.session.put("firstname", bookingDetails.getFirstname());
		context.session.put("lastname", bookingDetails.getLastname());
		logger.debug("Session firstname: "+context.session.get("firstname"));
		logger.debug("Session lastname: "+context.session.get("lastname"));
		context.response = context.requestSetup()
				.queryParams("firstname", context.session.get("firstname"), "lastname", context.session.get("lastname"))
				.when().get(context.session.get("endpoint").toString());	
		BookingId[] bookingIDs = RestAssuredResponseHandler.deserializedResponse(context.response, BookingId[].class);
		assertNotNull(bookingIDs,"Booking ID not found!!");
	}
	
	@When("user makes a request to view booking IDs from {string} to {string}")
	public void userMakesARequestToViewBookingFromTo(String checkin, String checkout) {
		context.response = context.requestSetup()
				.queryParams("checkin",checkin, "checkout", checkout)
				.when().get(context.session.get("endpoint").toString());	
	}
	
}


