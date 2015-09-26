package com.holler.holler_dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.holler.holler_dao.util.CommonUtil;

@MappedSuperclass
public class BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6819224873999485854L;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date created;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date lastModified;
	
	@PrePersist
	@PreUpdate
	void setPreDefaultValues(){
		if(CommonUtil.isNull(created)){
			created = new Date();
		}
		lastModified = new Date();
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
}
