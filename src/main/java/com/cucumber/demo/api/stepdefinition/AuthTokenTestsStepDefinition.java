package com.cucumber.demo.api.stepdefinition;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.cucumber.demo.api.utils.TestContext;

import io.cucumber.java.en.When;

public class AuthTokenTestsStepDefinition {

	private TestContext context;
	private static final Logger logger = LogManager.getLogger(AuthTokenTestsStepDefinition.class);
	
	public AuthTokenTestsStepDefinition(TestContext context) {
		this.context = context;
	}
	
	@When("user creates a auth token with credential {string} & {string}")
	public void userCreatesAAuthTokenWithCredential(String username, String password) {
		JSONObject credentials = new JSONObject();
		credentials.put("username", username);
		credentials.put("password", password);
		context.response = context.requestSetup().body(credentials.toString())
				.when().post(context.session.get("endpoint").toString());
		String token = context.response.path("token");
		logger.info("Auth Token: "+token);
		context.session.put("token", "token="+token);	
	}
}
