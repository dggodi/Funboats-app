package com.funboats.model;

import java.io.Serializable;
import javax.persistence.*;

//@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	private String userName;
	private String password;
	private String role;
	private Profile profile;
	
	public User(){}
	
	public User(User user) {
		this.userId = user.userId;
		this.userName = user.userName;
		this.password = user.password;
	}

	public User(long userId, String userName, String password) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userNamee) {
		this.userName = userNamee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}

