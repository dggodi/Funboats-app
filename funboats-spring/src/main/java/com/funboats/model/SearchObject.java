package com.funboats.model;

/**
 * class for json objects being searched for
 */
public class SearchObject {
	private Long id;				// represents id of pojo
	private String search;			// represents the obj being searched
	public Long getId() {			
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	
}
