package com.funboats.model;

/**
 * class for user session authorities
 */
public class Authority {

	private Long authority_id;
	private String role;

	public Authority(String role) {
		this.role = role;
	}
	
	public Long getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(Long authority_id) {
		this.authority_id = authority_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
}
