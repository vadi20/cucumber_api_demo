package com.cucumber.demo.api.stepdefinition;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Map;

import org.json.JSONObject;

import com.cucumber.demo.api.data.BookingDetails;
import com.cucumber.demo.api.utils.RestAssuredResponseHandler;
import com.cucumber.demo.api.utils.TestContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class UpdateBookingsTestStepDefinition {

	private TestContext context;
	//private static final Logger logger = LogManager.getLogger(UpdateBookingsTestStepDefinition.class);
	
	public UpdateBookingsTestStepDefinition(TestContext context) {
		this.context = context;
	}
	
	@When("user updates the details of a booking")
	public void userUpdatesABooking(DataTable dataTable) {
		Map<String,String> bookingData = dataTable.asMaps().get(0);
		JSONObject bookingBody = new JSONObject();
		bookingBody.put("firstname", bookingData.get("firstname"));
		bookingBody.put("lastname", bookingData.get("lastname"));
		bookingBody.put("totalprice", Integer.valueOf(bookingData.get("totalprice")));
		bookingBody.put("depositpaid", Boolean.valueOf(bookingData.get("depositpaid")));
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", (bookingData.get("checkin")));
		bookingDates.put("checkout", (bookingData.get("checkout")));
		bookingBody.put("bookingdates", bookingDates);
		bookingBody.put("additionalneeds", bookingData.get("additionalneeds"));

		context.response = context.requestSetup()
				.header("Cookie", context.session.get("token").toString())
				.pathParam("bookingID", context.session.get("bookingID"))
				.body(bookingBody.toString())
				.when().put(context.session.get("endpoint")+"/{bookingID}");

		BookingDetails bookingDetailsDTO = RestAssuredResponseHandler.deserializedResponse(context.response, BookingDetails.class);
		assertNotNull(bookingDetailsDTO,"Booking not created");
	}
	
	
	@When("user makes a request to update first name {string} & Last name {string}")
	public void userMakesARequestToUpdateFirstNameLastName(String firstName, String lastName) {
		JSONObject body = new JSONObject();
		body.put("firstname", firstName);
		body.put("lastname", lastName);
		
		context.response = context.requestSetup()
				.header("Cookie", context.session.get("token").toString())
				.pathParam("bookingID", context.session.get("bookingID"))
				.body(body.toString())
				.when().patch(context.session.get("endpoint")+"/{bookingID}");
		
		BookingDetails bookingDetails = RestAssuredResponseHandler.deserializedResponse(context.response, BookingDetails.class);
		assertNotNull(bookingDetails,"Booking not created");
		assertEquals(bookingDetails.getFirstname(), firstName, "First Name did not match");
		assertEquals(bookingDetails.getLastname(), lastName, "Last Name did not match");
	}
}
