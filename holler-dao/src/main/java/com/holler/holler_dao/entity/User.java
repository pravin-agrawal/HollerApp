package com.holler.holler_dao.entity;

import com.holler.holler_dao.entity.enums.UserStatusType;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464369837316338488L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "about")
	private String about;

	@Column(name = "avg_rating")
	private Float rating = 0f;
	
	@Column(name = "pic")
	private String pic;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private UserStatusType status;
	
	@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(name = "user_tag",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name="tag_id"))
	private Set<Tags> tags;

	@Column(name = "current_location")
	private String currentLocation;

	@Column(name = "current_address")
	private String currentAddress;

	@Column(name = "job_discovery_limit")
	private Integer jobDiscoveryLimit = 10;
	
	@Column(name = "compensation_range_min")
	private Integer compensationRangeMin = 0;

	@Column(name = "compensation_range_max")
	private Integer compensationRangeMax = 50000;

	@Column(name = "push_notification")
	private Integer pushNotification = 1;

	@Column(name = "is_user_verified")
	private boolean userVerified;
	
	@Column(name = "source_platform")
	private String sourcePlatform;
	
	@Column(name = "gender")
	private String gender;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	public Set<Tags> getTags() {
		return tags;
	}
	public void setTags(Set<Tags> tags) {
		this.tags = tags;
	}

	public UserStatusType getStatus() {
		return status;
	}

	public void setStatus(UserStatusType status) {
		this.status = status;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Integer getJobDiscoveryLimit() {
		return jobDiscoveryLimit;
	}

	public void setJobDiscoveryLimit(Integer jobDiscoveryLimit) {
		this.jobDiscoveryLimit = jobDiscoveryLimit;
	}

	public Integer getCompensationRangeMin() {
		return compensationRangeMin;
	}

	public void setCompensationRangeMin(Integer compensationRangeMin) {
		this.compensationRangeMin = compensationRangeMin;
	}

	public Integer getCompensationRangeMax() {
		return compensationRangeMax;
	}

	public void setCompensationRangeMax(Integer compensationRangeMax) {
		this.compensationRangeMax = compensationRangeMax;
	}

	public Integer getPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(Integer pushNotification) {
		this.pushNotification = pushNotification;
	}

	public boolean isUserVerified() {
		return userVerified;
	}

	public void setUserVerified(boolean isUserVerified) {
		this.userVerified = isUserVerified;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getSourcePlatform() {
		return sourcePlatform;
	}

	public void setSourcePlatform(String sourcePlatform) {
		this.sourcePlatform = sourcePlatform;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public static User constructUserForSignUp(String name, String email, String phoneNumber, String platform) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setSourcePlatform(platform);
		user.setStatus(UserStatusType.ACTIVE);
		return user;
	}

	public static User constructUserForSocialPlatformSignUp(String name, String email, String gender, String profilePic, String platform) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setGender(gender);
		user.setPic(profilePic);
		user.setSourcePlatform(platform);
		user.setStatus(UserStatusType.ACTIVE);
		return user;
	}
	
	public Double[] getLatLongFromCurrentLocation() {
		Double[] latLong = new Double[2];
		String[] latLongString = this.currentLocation.split(",");
		latLong[0] = Double.parseDouble(latLongString[0]);
		latLong[1] = Double.parseDouble(latLongString[1]);
		return latLong;
	}

}
