package com.holler.holler_dao;

import com.holler.holler_dao.entity.Emails;
import com.holler.holler_dao.entity.Faqs;
import org.springframework.stereotype.Repository;

/**
 * Created by pravina on 22/08/16.
 */
@Repository
public class EmailDaoImpl extends BaseDaoImpl<Emails> implements EmailDao {

    public EmailDaoImpl() {
        super(Emails.class);
    }
}
