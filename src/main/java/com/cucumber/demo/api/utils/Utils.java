package com.cucumber.demo.api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Utils {

	public static String getFutureDate(int noOfDays) {
		LocalDate futureDate = LocalDate.now().plusDays(noOfDays);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return futureDate.format(formatter);
	}
	
	public static void createBookingTempFilewithId(String bookingID) {
		Path path = Paths.get("bookingTemp.txt");
		try {
			Files.write(path, Arrays.asList(bookingID));
		} catch (IOException e) {
		}
	}
	
	public static int retreiveBookingIdFromTempFile() {
		Path path = Paths.get("bookingTemp.txt");
		try {
			String content = Files.readString(path);
			return Integer.valueOf(content.trim());
		} catch (IOException e) {
			return -1;
		}

	}

}
