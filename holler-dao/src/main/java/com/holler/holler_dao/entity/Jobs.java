package com.holler.holler_dao.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	@Column(name = "status")
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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



	


}
