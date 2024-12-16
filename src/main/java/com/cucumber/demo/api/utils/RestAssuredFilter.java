package com.cucumber.demo.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class RestAssuredFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(RestAssuredFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        
        logger.debug("-----------------------------------------Request-----------------------------------------");
        logger.debug(" Request Method => " + requestSpec.getMethod());
        logger.debug(" Request URI => " + requestSpec.getURI());
        logger.debug(" Request Header =>\n" + requestSpec.getHeaders());
        logger.debug(" Request Body => " + requestSpec.getBody());
        logger.debug("-----------------------------------------Response-----------------------------------------");
        logger.debug(" Response Status => "+ response.getStatusLine());
        logger.debug(" Response Header =>\n"+ response.getHeaders());
        logger.debug(" Response Body => " + response.getBody().asString());
        logger.debug("-----------------------------------------------------------------------------------------");
        return response;
	}

}
