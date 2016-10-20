package com.holler.holler_dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "emails")
public class Emails extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -48489231371179433L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "emaiId")
	private String emaiId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmaiId() {
		return emaiId;
	}

	public void setEmaiId(String emaiId) {
		this.emaiId = emaiId;
	}
}
