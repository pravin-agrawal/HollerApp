package com.holler.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.util.CommonUtil;

public class UserDTO {
	private int userId;
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
	private Set<Integer> tags;
	private Float avgRating;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
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
	public Set<Integer> getTags() {
		return tags;
	}
	public void setTags(Set<Integer> tags) {
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

	public static User constructUserDTO(UserDTO userDTO) {
		User user = new User();
		if(CommonUtil.isNotNull(userDTO.getUserId())){
			user.setId(userDTO.getUserId());
		}
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmailId());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setAbout(userDTO.getAbout());
		user.setPic(userDTO.getPic());
		user.setLastModified(new Date());
		return user;
	}

	public static List<UserDTO> constructUserDTOsForAcceptedJObs(List<User> users) {
		List<UserDTO> UserDTOs = new ArrayList<UserDTO>();
		for(User user:users){
			UserDTO userDTO = new UserDTO();
			userDTO.setUserId(user.getId());
			userDTO.setName(user.getName());
			userDTO.setPic(user.getPic());
			UserDTOs.add(userDTO);
		}

		return UserDTOs;
	}

}
