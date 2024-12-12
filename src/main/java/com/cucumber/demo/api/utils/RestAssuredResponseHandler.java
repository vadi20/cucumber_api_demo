package com.cucumber.demo.api.utils;

import java.io.IOException;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class RestAssuredResponseHandler {

	@SuppressWarnings("unchecked")
	public static <T> T  deserializedResponse(Response response, Class<?> T ){
		ObjectMapper mapper = new ObjectMapper();
		T responseDeserialized = null;
		try {
			responseDeserialized = (T) mapper.readValue(response.asString(), T);
		} catch (IOException e) {
		}
		return responseDeserialized;
	}
}
