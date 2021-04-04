package com.kavya.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	
	public static RequestSpecification requestSpecification;
	public static Response response;
	public Logger logger;
	
	public Logger setUp() {
		logger = Logger.getLogger("EmployeeRestAPI");
		PropertyConfigurator.configure("log4j.properties"); // mention configuration file
		logger.setLevel(Level.DEBUG);
		return logger;
	}

}
