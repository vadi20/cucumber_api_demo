package com.cucumber.demo.api.stepdefinition;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static com.cucumber.demo.api.utils.Utils.createBookingTempFilewithId;
import static com.cucumber.demo.api.utils.Utils.getFutureDate;
import static com.cucumber.demo.api.utils.Utils.retreiveBookingIdFromTempFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.cucumber.demo.api.data.Booking;
import com.cucumber.demo.api.data.BookingDetails;
import com.cucumber.demo.api.data.BookingId;
import com.cucumber.demo.api.utils.RestAssuredResponseHandler;
import com.cucumber.demo.api.utils.TestContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class CreateBookingsTestsDefinition {
	
	
	private static final Logger logger = LogManager.getLogger(CreateBookingsTestsDefinition.class);
	private TestContext context;
	//private static String bookingId;
	//private static List<Integer>  allBookingIds ;
	
	
	public  CreateBookingsTestsDefinition(TestContext context) {
		this.context = context;
	}
	
	
	@When("user creates a booking")
	public void userCreatesABooking(DataTable dataTable) {
		
		String checkin =  getFutureDate(1);
		String checkout = getFutureDate(10);
		
		Map<String,String> bookingData = dataTable.asMaps().get(0);
		JSONObject bookingBody = new JSONObject();
		bookingBody.put("firstname", bookingData.get("firstname"));
		bookingBody.put("lastname", bookingData.get("lastname"));
		bookingBody.put("totalprice", Integer.valueOf(bookingData.get("totalprice")));
		bookingBody.put("depositpaid", Boolean.valueOf(bookingData.get("depositpaid")));
		JSONObject bookingDates = new JSONObject();


		bookingDates.put("checkin",checkin);
		bookingDates.put("checkout", checkout);
		bookingBody.put("bookingdates", bookingDates);
		bookingBody.put("additionalneeds", bookingData.get("additionalneeds"));

		context.response = context.requestSetup().body(bookingBody.toString())
				.when().post(context.session.get("endpoint").toString());

		Booking booking = RestAssuredResponseHandler.deserializedResponse(context.response, Booking.class);
		assertNotNull(booking,"Booking not created");
		String bookingId = booking.getBookingid();
		logger.debug("Newly created booking ID: "+booking.getBookingid());
		//context.session.put("bookingID", booking.getBookingid());
		validateBookingData(new JSONObject(bookingData), booking,checkin, checkout);
		createBookingTempFilewithId(String.valueOf(bookingId));

	}
	

	private void validateBookingData(JSONObject bookingData, Booking booking, String checkin, String checkout) {
		logger.debug(bookingData);
		assertNotNull("Booking ID missing", booking.getBookingid());
		assertEquals(booking.getBooking().getFirstname(),  bookingData.get("firstname"), "First Name did not match");
		assertEquals(booking.getBooking().getLastname(), bookingData.get("lastname"),"Last Name did not match");
		assertEquals(booking.getBooking().getTotalprice(), bookingData.get("totalprice"), "Total Price did not match" );
		assertEquals(booking.getBooking().getDepositpaid(), bookingData.get("depositpaid"), "Deposit Paid did not match" );
		assertEquals(booking.getBooking().getAdditionalneeds(), bookingData.get("additionalneeds"), "Additional Needs did not match");
		assertEquals(booking.getBooking().getBookingdates().getCheckin(),checkin, "Check in Date did not match");
		assertEquals( booking.getBooking().getBookingdates().getCheckout(), checkout,"Check out Date did not match");
	}
	
	@When("user makes a request to view details of a new booking ID")
	public void userMakesARequestToViewDetailsOfBookingID() {
		
		BookingDetails bookingDetails = null;
		int bookingId = retreiveBookingIdFromTempFile();
		
		if(bookingId != -1) {
			context.session.put("bookingID", bookingId);
			logger.debug("Session BookingID: "+context.session.get("bookingID"));
			context.response = context.requestSetup().pathParam("bookingID", context.session.get("bookingID"))
					.when().get(context.session.get("endpoint")+"/{bookingID}");
			bookingDetails = RestAssuredResponseHandler.deserializedResponse(context.response, BookingDetails.class);
		}
		assertNotNull(bookingDetails,"Booking Details not found!!");
	}
	
	@When("user makes a request to view details of a new booking ID by Name")
	public void userMakesARequestToViewBookingIDByUserName(DataTable dataTable) {
		
		Map<String,String> bookingData = dataTable.asMaps().get(0);

		context.session.put("firstname", bookingData.get("firstname"));
		context.session.put("lastname", bookingData.get("lastname"));
		logger.debug("Session firstname: "+context.session.get("firstname"));
		logger.debug("Session lastname: "+context.session.get("lastname"));
		context.response = context.requestSetup()
				.queryParams("firstname", context.session.get("firstname"), "lastname", context.session.get("lastname"))
				.when().get(context.session.get("endpoint").toString());	
		BookingId[] bookingIDs = RestAssuredResponseHandler.deserializedResponse(context.response, BookingId[].class);
		
		List<String> bookingList = new ArrayList<String>();
		for(BookingId booking:bookingIDs) {
			bookingList.add(booking.getBookingid());
		}
		int bookingId = retreiveBookingIdFromTempFile();
		if(!bookingList.contains(String.valueOf( bookingId))) {
			assertTrue(false,"Booking Details not found!!");
		}

	}
}
