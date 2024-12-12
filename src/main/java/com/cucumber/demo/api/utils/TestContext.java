package com.cucumber.demo.api.utils;

import java.util.HashMap;
import java.util.Map;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class TestContext {

	public Map<String, Object> session = new HashMap<String, Object>();
	public Response response;
	private static final String CONTENT_TYPE = ApplicationProperties.getProperty("contentType");

	public RequestSpecification requestSetup() {
		RestAssured.reset();
		RestAssured.baseURI = ApplicationProperties.getProperty("baseURL");	
		
		return RestAssured.given()
				.filter(new RestAssuredFilter())	
				.contentType(CONTENT_TYPE)
				.accept(CONTENT_TYPE);
	}

}
