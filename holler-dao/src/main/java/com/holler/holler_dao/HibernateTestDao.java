package com.holler.holler_dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.holler.holler_dao.entity.HibernateTest;
@Repository
public class HibernateTestDao {
	
		@PersistenceContext
		EntityManager entityManager;
		
		@Transactional
	    public void save(HibernateTest hibernateTest) {
	        entityManager.persist(hibernateTest);
	    }
}
