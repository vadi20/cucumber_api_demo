package com.cucumber.demo.api.data;


public class Booking {

	 private BookingDetails booking;

	    private String bookingid;

	    public BookingDetails getBooking ()
	    {
	        return booking;
	    }

	    public void setBooking (BookingDetails booking)
	    {
	        this.booking = booking;
	    }

	    public String getBookingid ()
	    {
	        return bookingid;
	    }

	    public void setBookingid (String bookingid)
	    {
	        this.bookingid = bookingid;
	    }

}
