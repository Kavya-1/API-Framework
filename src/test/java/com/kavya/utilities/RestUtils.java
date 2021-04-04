package com.kavya.utilities;


import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils {
	
	public static String getEmployeeName() {
		String randomString = RandomStringUtils.randomAlphabetic(3);
		return "Kavya"+randomString;
	}
	
	public static String getEmailId() {
		String randomString = RandomStringUtils.randomAlphabetic(3);
		return "Kavya"+randomString+"@gmail.com";
	}

}
