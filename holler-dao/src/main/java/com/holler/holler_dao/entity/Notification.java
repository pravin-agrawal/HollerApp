package com.holler.holler_dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.holler.holler_dao.entity.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notification")
@Getter @Setter
public class Notification extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464369837316338488L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="from_user")
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name="to_user")
	private User toUser;

	@Enumerated(EnumType.STRING)
	@Column(name = "notification_type")
	private NotificationType type;

	@Column(name = "is_read")
	boolean isRead = false;

	@Column(name = "is_seen")
	boolean isSeen = false;
	
	@Column(name = "object_id")
	private int objectId;

}
