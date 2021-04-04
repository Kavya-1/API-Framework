package com.kavya.testcases;

import java.util.HashMap;
import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.kavya.base.TestBase;
import com.kavya.utilities.RestUtils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

@Listeners(com.kavya.utilities.Listeners.class)
public class TCD03_CreateUser extends TestBase{
	JSONObject requestBody = new JSONObject();
	
	@BeforeClass
	public void setUpCreateUser() {
		logger = setUp();
		logger.info("Creating one user record");
		RestAssured.baseURI = "https://gorest.co.in/public-api";
		requestSpecification = RestAssured.given();
		
		HashMap<String, String> headers = new HashMap<String, String>();
		logger.info("Setting up headers");
		headers.put("Authorization", "Bearer f082e2de897cd83cac34170b24e3c6272dba197323e0338c635b89d32cbab861");
		headers.put("Content-Type", "application/json");
		requestSpecification.headers(headers);
		
		logger.info("Preparing request body");
		requestBody.put("name", RestUtils.getEmployeeName());
		requestBody.put("email",RestUtils.getEmailId());
		requestBody.put("gender", "Female");
		requestBody.put("status", "Active");
	}
	
	@Test(priority = 0)
	public void createUser() {
		logger.info("Creating one user");
		response = requestSpecification.body(requestBody.toJSONString()).post("/users");
		System.out.println(response.getBody().asPrettyString());
	}
	
	@Test(priority = 1)
	public void validateStatusCode() {
		logger.info("Validating status code");
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2)
	public void validateResponseBody() {
		JsonPath jsonBody = response.jsonPath();
		System.out.println("User created with id :"+jsonBody.getString("data.id"));
		System.out.println("Code :"+jsonBody.getString("code"));
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("Finished create employees test execution");
	}
}
