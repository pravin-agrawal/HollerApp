package com.holler.bean;

import com.holler.holler_dao.entity.User;

public class UserDTO {
	private String name;
	private String emailId;
	private String phoneNumber;
	private String status;
	private String pic;
	private String country;
	private String city;
	private String gender;
	private boolean allowSms = false;
	private String about;
	private String tags;
	private Float avgRating;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public boolean isAllowSms() {
		return allowSms;
	}
	public void setAllowSms(boolean allowSms) {
		this.allowSms = allowSms;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Float avgRating) {
		this.avgRating = avgRating;
	}

	public static UserDTO getDtoForUserProfile(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setName(user.getName());
		userDTO.setEmailId(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setAbout(user.getAbout());
		userDTO.setPic(user.getPic());
		return userDTO;
	}
	
}
