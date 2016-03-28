package com.holler.holler_dao.util;


public class OTP
{
    
    public static Integer getOtp(){
    	Integer val = ((int)(Math.random()*9000)+1000);   	
    	return val;
    }
}