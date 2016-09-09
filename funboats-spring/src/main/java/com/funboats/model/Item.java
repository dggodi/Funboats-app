package com.funboats.model;

import java.io.Serializable;
import java.util.Date;

/**
 * class for item detailed description
 */
public class Item implements Serializable{

	private static final long serialVersionUID = 3183324807708599480L;
	
	private Long item_id;

	private int hours;
	private int displacement;	// how much air moves through a single cycle
	private String color;		// color o jetski
	private String category;	// type of jetski
	private String seating;		// seating capacity
	private String vinNo;		// vin number
	private String engine;		// engine
	private String pumpType;	// type of pump
	private int fuelcapacity;	// fuel capcacity
	private Date updatedAt;
	
	private ItemImage image;

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getDisplacement() {
		return displacement;
	}

	public void setDisplacement(int displacement) {
		this.displacement = displacement;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSeating() {
		return seating;
	}

	public void setSeating(String seating) {
		this.seating = seating;
	}

	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getPumpType() {
		return pumpType;
	}

	public void setPumpType(String pumpType) {
		this.pumpType = pumpType;
	}

	public int getFuelcapacity() {
		return fuelcapacity;
	}

	public void setFuelcapacity(int fuelcapacity) {
		this.fuelcapacity = fuelcapacity;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ItemImage getImage() {
		return image;
	}

	public void setImage(ItemImage image) {
		this.image = image;
	}
	
	@Override
	public String toString(){
		return (color + "  " + category + "  " + engine + "  " + hours + "  " + displacement + "  " + pumpType + "  " + fuelcapacity + "  " + vinNo);
	}
	
}
