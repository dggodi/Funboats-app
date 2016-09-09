package com.funboats.model;

import java.io.Serializable;
import java.util.Date;

public class ItemImage implements Serializable {

	private static final long serialVersionUID = -2042369897704025008L;
	
	private Long imageId;
	private String contentType;
	private byte[] blob;
	private Date updatedAt;
	
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getBlob() {
		return blob;
	}
	public void setBlob(byte[] blob) {
		this.blob = blob;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
