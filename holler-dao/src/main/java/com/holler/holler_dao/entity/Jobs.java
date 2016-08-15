package com.holler.holler_dao.entity;

import com.holler.holler_dao.entity.enums.JobStatusType;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "jobs")
public class Jobs extends BaseEntity{
	private static final long serialVersionUID = -5464369837316338488L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "userId")
	private User user;
	
	@Column(name = "job_title")
	private String title;

	@Column(name = "job_description")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private JobStatusType status;

	@Column(name = "special_requirement")
	private String specialRequirement;
	
	@Column(name = "gender_preference")
	private Integer genderPreference;
	
	@Column(name = "compensation")
	private Integer compensation;
	
	@ManyToMany(cascade = {CascadeType.REFRESH})
	@JoinTable(name = "job_tag",
		joinColumns = @JoinColumn(name = "job_id"),
		inverseJoinColumns = @JoinColumn(name="tag_id"))
	private Set<Tags> tags;

	@Column(name = "job_location")
	private String jobLocation;

	@Column(name = "job_address")
	private String jobAddress;

	@Column(name = "inappropriate_content")
	boolean inappropriateContent = false;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "job_date")
	protected Date jobDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Tags> getTags() {
		return tags;
	}

	public void setTags(Set<Tags> tags) {
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JobStatusType getStatus() {
		return status;
	}

	public void setStatus(JobStatusType status) {
		this.status = status;
	}

	public String getSpecialRequirement() {
		return specialRequirement;
	}

	public void setSpecialRequirement(String specialRequirement) {
		this.specialRequirement = specialRequirement;
	}

	public Integer getGenderPreference() {
		return genderPreference;
	}

	public void setGenderPreference(Integer genderPreference) {
		this.genderPreference = genderPreference;
	}

	public Integer getCompensation() {
		return compensation;
	}

	public void setCompensation(Integer compensation) {
		this.compensation = compensation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}

	public String getJobAddress() {
		return jobAddress;
	}

	public Date getJobDate() {
		return jobDate;
	}
	
	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	public boolean isInappropriateContent() {
		return inappropriateContent;
	}

	public void setInappropriateContent(boolean inappropriateContent) {
		this.inappropriateContent = inappropriateContent;
	}

	public Double[] getLatLongFromJobLocation() {
		Double[] latLong = new Double[2];
		String[] latLongString = this.jobLocation.split(",");
		latLong[0] = Double.parseDouble(latLongString[0]);
		latLong[1] = Double.parseDouble(latLongString[1]);
		return latLong;
	}

}
