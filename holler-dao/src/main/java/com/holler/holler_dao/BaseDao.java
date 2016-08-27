
package com.holler.holler_dao;

import java.util.*;

public interface BaseDao<T> {
	void save(T entity);

	void save(Collection<T> entity);

	T update(T entity);

	void remove(T entity);

	T findById(int entityId);

	T load(Object id);

	List<T> findAll();

	List<T> findPaginatedResults(String queryStr, int currentPage, int pageSize);

	List<T> findParamsPaginatedResults(String queryStr, Object[] params, int currentPage, int pageSize);

	List<T> findParamsPaginatedResults(String queryStr, Map<String, Object> params, int currentPage, int pageSize);

	List<T> getResults(String queryStr);

	void refresh(T object);

	void clearEntityManager();

	void flush();

	<V> List<V> getSQLQueryResult(String query, Class<V> klass);

	Object getSQLQueryResult(String query);

	List<T> findAllWithJoin(String property);

	Object getQueryWithParamsResult(String queryString,
			Map<String, Object> params);

    void update(Collection<T> entities);

}
