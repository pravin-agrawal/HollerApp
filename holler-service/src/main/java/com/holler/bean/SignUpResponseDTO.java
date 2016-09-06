package com.holler.bean;


public class SignUpResponseDTO {
	
	private String token;
	private int userID;
	private String email;
	private String phoneNumber;
	private String fullName;
	private String profilePictureURL;
	private float rating;
	private boolean isUserVerified;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getProfilePictureURL() {
		return profilePictureURL;
	}
	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public boolean isUserVerified() {
		return isUserVerified;
	}

	public void setIsUserVerified(boolean isUserVerified) {
		this.isUserVerified = isUserVerified;
	}

	public SignUpResponseDTO(){
		
	}
	public SignUpResponseDTO(String token, int userID, String email,
			String phoneNumber, String fullName, String profilePictureURL, float rating, boolean isUserVerified) {
		super();
		this.token = token;
		this.userID = userID;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
		this.profilePictureURL = profilePictureURL;
		this.rating =rating;
		this.isUserVerified = isUserVerified;
	}

	public SignUpResponseDTO(String token, int userID, String email,
							 String phoneNumber, String fullName, String profilePictureURL) {
		super();
		this.token = token;
		this.userID = userID;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
		this.profilePictureURL = profilePictureURL;
	}

	
	
	
}
