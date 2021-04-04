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
public class TCD04_UpdateUser extends TestBase{

	JSONObject requestBody = new JSONObject();
	String userForUpdate=null;
	String updatedName =null;
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
		response = requestSpecification.body(requestBody.toJSONString()).post("/users");
		System.out.println(response.getBody().asPrettyString());
		JsonPath jsonBody = response.jsonPath();
		userForUpdate = jsonBody.getString("data.id");
		logger.info("User created with id = "+userForUpdate);
	}
	
	@Test(priority = 0)
	public void updateUser() {
		HashMap<String, String> queryParams = new HashMap<String, String>();
		updatedName = RestUtils.getEmployeeName();
		queryParams.put("name", updatedName);
		logger.info("Name to be updated is ="+updatedName);
		requestSpecification.queryParams(queryParams);
		response = requestSpecification.patch("/users/"+userForUpdate);
		System.out.println(response.getBody().asPrettyString());

	}
	
	@Test(priority = 1)
	public void validateUpdation() {
		JsonPath jsonPath = response.jsonPath();
		Assert.assertEquals(jsonPath.getString("data.name"), updatedName);
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("Finished update employees test execution");
	}
}
