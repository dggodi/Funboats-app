package com.funboats.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * class for user object
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private String username;
	private String password;
	public boolean enabled;
	private Date updatedAt;
	private Set<Authority> authorities = new HashSet<>();
	
	private Profile profile;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String usernamee) {
		this.username = usernamee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	/**
     * return the generated encrypted password
     * @param password
     * @return String
     */
    public static String encryptPassword(String password)
    {
    	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	return passwordEncoder.encode(password);
    }
	
	
}

