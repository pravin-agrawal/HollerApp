package com.holler.holler_dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "app_version")
@Getter
@Setter
public class AppVersion extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -48489231371179433L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "version")
    private String version;
}
