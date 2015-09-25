package com.holler.holler_dao;

import java.util.Map;

public class QueryDao {
	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public String getQueryString(String queryId){
		String query = (String)map.get(queryId);
		return query;
	}
}
