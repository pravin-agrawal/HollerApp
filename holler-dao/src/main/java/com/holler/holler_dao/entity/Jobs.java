package com.holler.holler_dao.entity;

import com.holler.holler_dao.entity.enums.JobMedium;
import com.holler.holler_dao.entity.enums.JobStatusType;
import com.holler.holler_dao.entity.enums.JobType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "jobs")
@Getter @Setter
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
	
	@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
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

	@Enumerated(EnumType.STRING)
	@Column(name = "job_medium")
	private JobMedium jobMedium;

	@Enumerated(EnumType.STRING)
	@Column(name = "job_type")
	private JobType jobType;

	public Double[] getLatLongFromJobLocation() {
		Double[] latLong = new Double[2];
		String[] latLongString = this.jobLocation.split(",");
		latLong[0] = Double.parseDouble(latLongString[0]);
		latLong[1] = Double.parseDouble(latLongString[1]);
		return latLong;
	}

}
