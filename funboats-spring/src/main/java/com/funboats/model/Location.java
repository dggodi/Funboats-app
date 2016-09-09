package com.funboats.model;

import java.io.Serializable;
import java.util.Date;

/**
 * class for jetski location 
 */
public class Location implements Serializable {

	private static final long serialVersionUID = 2100507665617453434L;
	
	private Long locationId;
	private String name;			// name of location
	private Date updatedAt;
	
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
