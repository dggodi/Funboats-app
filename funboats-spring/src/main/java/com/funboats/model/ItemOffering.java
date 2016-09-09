package com.funboats.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * class for item basic description
 */
public class ItemOffering implements Serializable {

	private static final long serialVersionUID = 3025745593622380698L;
	
	private Long itemOfferingId;
	private String brand;			// jetski brand	
	private String model;			// jetski model	
	private int year;
	private int cost;
	private String descript;		// description of jetski
	private String location;		// location being sold
	private String username;		// user selling jetski
	private Date updatedAt;

	private Item item;				// FK to detailed description

	public Long getItemOfferingId() {
		return itemOfferingId;
	}
	public void setItemOfferingId(Long itemOfferingId) {
		this.itemOfferingId = itemOfferingId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString(){
		return (itemOfferingId + "  " + brand + "  " + model + "  " + year + "  " + cost + "  " + descript + "  " + location + "  " + username);
	}
	
}
