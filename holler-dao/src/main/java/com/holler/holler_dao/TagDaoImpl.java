package com.holler.holler_dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.holler.holler_dao.common.SQLQueryIds;
import com.holler.holler_dao.entity.Jobs;

import com.holler.holler_dao.entity.ParentTags;
import org.apache.commons.lang3.StringUtils;
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

	public List<Tags> fetchTagsForUserHomePage(Integer userId) {
		String sql = queryDao.getQueryString(SQLQueryIds.GET_TAGS_FOR_USER_HOME_PAGE);
		Query queryObject = entityManager.createNativeQuery(sql, Tags.class).setParameter("userId", userId);
		List<Tags> resultList = queryObject.getResultList();
		return resultList;
	}
	
	public void saveParentTagImageUrl(Integer tagId, String tagUrl){
		Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.SET_PARENT_TAG_IMAGE_URL));
		query.setParameter("tagId", tagId);
		query.setParameter("tagUrl", tagUrl);
		query.executeUpdate();
	}
	
	public void saveChildTagImageUrl(Integer tagId, String tagUrl){
		Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.SET_CHILD_TAG_IMAGE_URL));
		query.setParameter("tagId", tagId);
		query.setParameter("tagUrl", tagUrl);
		query.executeUpdate();
	}

	public List<Tags> getAllChildTags(){
		Query query = entityManager.createNativeQuery("SELECT * FROM tags ORDER BY tag_name",Tags.class);
		List<Tags> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<ParentTags> fetchAllParentTagWithChildTags() {
		Query query = entityManager.createNativeQuery("SELECT * FROM parent_tags ORDER BY parent_tag_name",ParentTags.class);
		List<ParentTags> resultList = query.getResultList();
		return resultList;
	}


}
