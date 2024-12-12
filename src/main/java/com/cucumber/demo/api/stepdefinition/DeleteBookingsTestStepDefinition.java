package com.cucumber.demo.api.stepdefinition;



import com.cucumber.demo.api.utils.TestContext;

import io.cucumber.java.en.When;
import io.restassured.http.Header;

public class DeleteBookingsTestStepDefinition {

	
	private TestContext context;

	public DeleteBookingsTestStepDefinition(TestContext context) {
		this.context = context;
	}
	
	@When("user makes a request to delete booking")
	public void userMakesARequestToDeleteBookingWithBasicAuth() {
		Header header = new Header("Cookie", context.session.get("token").toString());
		context.response = context.requestSetup()
				.header(header)
				.pathParam("bookingID", context.session.get("bookingID"))
				.when().delete(context.session.get("endpoint")+"/{bookingID}");
	}
}
