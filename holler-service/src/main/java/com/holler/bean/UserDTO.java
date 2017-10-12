package com.holler.bean;

import java.util.*;

import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.UserType;
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
	private Map<Integer, String> tagsMap;
	private Float avgRating;
	private boolean userVerified;
	private String facebookLink;
	private String linkedinLink;
	private String youtubeLink;
	private String otherLink;
	private String userType;
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

	public void setTagsMap(Map<Integer, String> tagsMap) {
		this.tagsMap = tagsMap;
	}

	public Map<Integer, String> getTagsMap() {
		return tagsMap;
	}

	public void setIsUserVerified(boolean isUserVerified) {
		this.userVerified = isUserVerified;
	}

	public boolean isUserVerified() {
		return userVerified;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getLinkedinLink() {
		return linkedinLink;
	}

	public void setLinkedinLink(String linkedinLink) {
		this.linkedinLink = linkedinLink;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public String getOtherLink() {
		return otherLink;
	}

	public void setOtherLink(String otherLink) {
		this.otherLink = otherLink;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static UserDTO getDtoForUserProfile(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmailId(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setAbout(user.getAbout());
		userDTO.setPic(user.getPic());
		userDTO.setAvgRating(user.getRating());
		userDTO.setIsUserVerified(user.isUserVerified());
		userDTO.setFacebookLink(user.getUserDetails().getFacebookProfile());
		userDTO.setLinkedinLink(user.getUserDetails().getLinkedinProfile());
		userDTO.setYoutubeLink(user.getUserDetails().getYoutubeLink());
		userDTO.setOtherLink(user.getUserDetails().getOtherLink());
		if(CommonUtil.isNotNull(user.getUserType())){
			userDTO.setUserType(user.getUserType().name());
		}
		Map<Integer, String> tagMaps = new HashMap<Integer, String>();
		for (Tags tag : CommonUtil.safe(user.getTags())) {
			tagMaps.put(tag.getId(), tag.getTagName());
		}
		userDTO.setTagsMap(tagMaps);
		return userDTO;
	}

	public static User setUserDataToUpdate(UserDTO userDTO, User user) {
		String name = userDTO.getName().substring(0,1).toLowerCase() + userDTO.getName().substring(1);
		user.setName(name);
		user.setEmail(userDTO.getEmailId());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setAbout(userDTO.getAbout());
		user.setPic(userDTO.getPic());
		user.setLastModified(new Date());
		user.getUserDetails().setFacebookProfile(userDTO.getFacebookLink());
		user.getUserDetails().setLinkedinProfile(userDTO.getLinkedinLink());
		user.getUserDetails().setYoutubeLink(userDTO.getYoutubeLink());
		user.getUserDetails().setOtherLink(userDTO.getOtherLink());
		if(userDTO.getUserType().equals(UserType.SELF.name())){
			user.setUserType(UserType.SELF);
		}else{
			user.setUserType(UserType.COMPANY);
		}
		return user;
	}

	

}
