package com.holler.holler_dao;

import org.springframework.stereotype.Repository;

import com.holler.holler_dao.entity.EmailSubjects;

/**
 * Created by pravina on 22/08/16.
 */
@Repository
public class EmailSubjectsDaoImpl extends BaseDaoImpl<EmailSubjects> implements EmailSubjectsDao {

    public EmailSubjectsDaoImpl() {
        super(EmailSubjects.class);
    }
}
