package com.cucumber.demo.api.stepdefinition;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class BaseTest {
	
	private static final Logger logger = LogManager.getLogger(BaseTest.class);

	@Before
	public void testStart(Scenario scenario) {
		logger.info("-----------------------------------------------------------------------------------------");
		logger.info("	Scenario: "+scenario.getName());
		logger.info("-----------------------------------------------------------------------------------------");
	}

}
