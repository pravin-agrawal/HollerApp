package com.holler.holler_dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_msg")
@Getter @Setter
public class Message extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -5464369837316338488L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_read")
    private boolean read;

    @Column(name = "from_user_id")
    private int fromUser;

    @Column(name = "to_user_id")
    private int toUser;

    @Column(name = "message")
    private String message;

}
