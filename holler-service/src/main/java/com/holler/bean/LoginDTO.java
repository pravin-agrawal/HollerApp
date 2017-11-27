package com.holler.bean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
	private String email;
	private String phoneNumber;
	private String otp;
	private String name;
}
