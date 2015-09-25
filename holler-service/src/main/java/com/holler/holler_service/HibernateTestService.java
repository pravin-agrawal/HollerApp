package com.holler.holler_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.holler_dao.HibernateTestDao;
import com.holler.holler_dao.entity.HibernateTest;

@Service
public class HibernateTestService {
	
	@Autowired
	HibernateTestDao hibernateTestDao;

	public void saveUsingEntityManager(){
		HibernateTest hibernateTest = new HibernateTest();
		System.out.println("HibernateTest Service called using EntityManager");
		hibernateTest.setTest("entity manager test");
		hibernateTestDao.save(hibernateTest);
	}
}
