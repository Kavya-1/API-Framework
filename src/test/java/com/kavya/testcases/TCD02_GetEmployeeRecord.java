package com.kavya.testcases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.kavya.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

@Listeners(com.kavya.utilities.Listeners.class)
public class TCD02_GetEmployeeRecord extends TestBase{
	
	@BeforeClass
	public void setUpUserAPI() {
		logger = setUp();
		logger.info("Fetching one employee record");
		RestAssured.baseURI = "https://gorest.co.in/public-api";
		requestSpecification = RestAssured.given();
	}
	
	@Test(priority = 0)
	public void getUserRecord() {
		logger.info("Fetching 1st employee");
		response = requestSpecification.get("/users/1403");
	}

	@Test(priority = 1)
	public void validateResponseBody() throws ParseException, IOException {
		logger.info("Validating response body");
		String responseBody = response.getBody().asString();
		Assert.assertTrue(responseBody!=null);
		
		// Writing the response to json file
		JSONParser parser = new JSONParser();
		String responseString = response.getBody().asString();
		File myObj = new File(System.getProperty("user.dir")+"/filename.json") ;
		myObj.createNewFile();
		FileWriter fileWriter = new FileWriter(myObj);
		fileWriter.write(responseString);
		fileWriter.close();
		
		// using jsonpath to query the json
		JsonPath jsonPath = response.jsonPath();
		System.out.println("Printing id = "+jsonPath.get("data.id"));
		
		
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
