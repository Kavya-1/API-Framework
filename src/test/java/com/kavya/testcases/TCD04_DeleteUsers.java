package com.kavya.testcases;

import java.util.HashMap;
import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.kavya.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

@Listeners(com.kavya.utilities.Listeners.class)
public class TCD04_DeleteUsers extends TestBase{
	
	String userIdToDelete = null;
	@BeforeClass
	public void setUpCreateUser() {
		logger = setUp();
		RestAssured.baseURI = "https://gorest.co.in/public-api";
		requestSpecification = RestAssured.given();

		HashMap<String, String> headers = new HashMap<String, String>();
		logger.info("Setting up headers");
		headers.put("Authorization", "Bearer f082e2de897cd83cac34170b24e3c6272dba197323e0338c635b89d32cbab861");
		headers.put("Content-Type", "application/json");
		requestSpecification.headers(headers);
	}
	
	@Test(priority = 0)
	public void getAllUsers() {
		response = requestSpecification.get("/users");
		JsonPath jsonPath = response.jsonPath();
		userIdToDelete = jsonPath.getString("data[1].id");
		logger.info("User id to delete is "+userIdToDelete);
		}
	
	@AfterMethod
	public void afterEachMethod(ITestResult result) {
		if(result.isSuccess()) {
			System.out.println("Test Passed******************");
		}
	}
	
	@Test(priority = 1)
	public void deleteUser() {
		response = requestSpecification.delete("/users/"+userIdToDelete);
		JsonPath jsonPath = response.jsonPath();
		Assert.assertEquals(jsonPath.getString("code"), "204");
	}
	
	@Test(priority = 2)
	public void getDeletedUsers() {
		response = requestSpecification.get("/users/"+userIdToDelete);
		JsonPath jsonPath = response.jsonPath();
		Assert.assertEquals(jsonPath.getString("code"), "404");
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("Finished delete employee test execution");
	}
}
