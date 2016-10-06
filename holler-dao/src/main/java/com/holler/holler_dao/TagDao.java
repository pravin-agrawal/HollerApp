package com.holler.holler_dao;

import java.util.List;
import java.util.Set;

import com.holler.holler_dao.entity.Tags;

public interface TagDao extends BaseDao<Tags> {
	
	public List<Tags> findbyIds(Set<Integer> tagIds);

	List<Tags> fetchTagsForUserHomePage(Integer userId);
	
	void saveParentTagImageUrl(Integer tagId, String tagUrl);
	
	void saveChildTagImageUrl(Integer tagId, String tagUrl);

}
