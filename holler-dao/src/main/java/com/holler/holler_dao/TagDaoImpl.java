package com.holler.holler_dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.holler.holler_dao.entity.Tags;

@Repository
public class TagDaoImpl extends BaseDaoImpl<Tags> implements TagDao {
	
	public TagDaoImpl() {
		super(Tags.class);
	}
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	QueryDao queryDao;
	
	@PersistenceContext
	EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Tags> findbyIds(Set<Integer> tagIds) {
		return  entityManager.createQuery("from " + Tags.class.getName() + " tags where tags.id in (:tagIds)", Tags.class)
				.setParameter("tagIds", tagIds).getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Tags> findbyIdsString(Integer tagIds) {
		return  entityManager.createQuery("from " + Tags.class.getName() + " tags where tags.id in (:tagIds)", Tags.class)
				.setParameter("tagIds", tagIds).getResultList();
	}
	

}
