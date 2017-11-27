package com.holler.util;

import java.util.UUID;

public class TokenGenerator {

	public static String generateToken(String email) {
		String token = email + "-" +UUID.randomUUID().toString(); 
		 return token;
	}

	public static String generateTokenWithPhoneNumber(String phoneNumber) {
		String token = phoneNumber + "-" +UUID.randomUUID().toString();
		return token;
	}
}
