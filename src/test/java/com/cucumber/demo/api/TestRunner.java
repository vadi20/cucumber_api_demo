package com.cucumber.demo.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		tags = "", 
		features = {"src/test/resources/features"}, 
		glue = {"com.cucumber.demo.api.stepdefinition"},
		plugin = { "pretty", "html:test-output/cucumber-reports.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
		 monochrome = true
		
		)
public class TestRunner extends AbstractTestNGCucumberTests{


}
