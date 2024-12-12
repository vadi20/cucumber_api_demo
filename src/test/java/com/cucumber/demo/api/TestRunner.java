package com.cucumber.demo.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		tags = "@DeleteBookings", 
		features = {"src/test/resources/features"}, 
		glue = {"com.cucumber.demo.api.stepdefinition"},
		plugin = {})
public class TestRunner extends AbstractTestNGCucumberTests{


}
