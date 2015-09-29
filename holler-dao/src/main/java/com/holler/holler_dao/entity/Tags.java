package com.holler.holler_dao.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tags extends BaseEntity{
	private static final long serialVersionUID = -5464369837316338488L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "tag_name")
	private String tagName;

	@ManyToMany(mappedBy = "tags")
	private Set<Jobs> jobs;
	
	@ManyToMany(mappedBy = "tags")
	private Set<User> user;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Set<Jobs> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Jobs> jobs) {
		this.jobs = jobs;
	}
	
	public Set<User> getUser() {
		return user;
	}
	public void setUser(Set<User> user) {
		this.user = user;
	}
	

	


}
