package com.holler.holler_dao.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parent_tags")
public class ParentTags extends BaseEntity{
	private static final long serialVersionUID = -5464369837316338488L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "parent_tag_name")
	private String parentTagName;

	@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(name = "parent_child_tag_join",
		joinColumns = @JoinColumn(name = "parent_tag_id"),
		inverseJoinColumns = @JoinColumn(name="child_tag_id"))
	private List<Tags> childTags;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getParentTagName() {
		return parentTagName;
	}

	public void setParentTagName(String parentTagName) {
		this.parentTagName = parentTagName;
	}

	public List<Tags> getChildTags() {
		return childTags;
	}

	public void setChildTags(List<Tags> childTags) {
		this.childTags = childTags;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void addChildTag(Tags tag){
		childTags.add(tag);
	}
}
