package com.kavya.testcases;

import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.kavya.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;

@Listeners(com.kavya.utilities.Listeners.class)
public class TCD01_Get_Employees extends TestBase{
	
	@BeforeClass
	public void getEmployeesSetUp() throws InterruptedException {
		logger = setUp();
		logger.info("Executiong first testcase Get All Employees");
		RestAssured.baseURI = "https://gorest.co.in/public-api";
		requestSpecification = RestAssured.given();
		Thread.sleep(3000);
	}
	
	@Test(priority = 0)
	public void getEmployees() {
		response = requestSpecification.get("/users");
	}
	
	@Test(priority = 1)
	public void validateResponseBody() {
		logger.info("Validating response body");
		String responseBody = response.getBody().asString();
		Assert.assertTrue(responseBody!=null);
	}
	
	@Test(priority = 2)
	public void checkStatusCode() {
		logger.info("Validating status code");
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 3)
	public void validateResponseTime() {
		logger.info("Validating response time");
		
		if(response.getTime() > 5000) {
			logger.warn("Response time is too high");
		}
		Assert.assertTrue(response.getTime() < 5000);
	}
	
	@Test(priority = 4)
	public void validateStatusLine() {
		logger.info("Validating status line");
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
	}
	
	@Test(priority = 5)
	public void validateContentType() {
		logger.info("Checking content type");
//		logger.info(response.getHeaders());
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("Finished get employees test execution");
	}
}
