package com.funboats.repositories.maps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import com.funboats.model.Authority;
import com.funboats.model.Profile;
import com.funboats.model.User;

/**
 * class maps the rows in users authorities and profiles to pojo classes 
 * User, Profile and Authority
 */
public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int row) throws SQLException {
		
		Profile profile = new Profile();
		profile.setProfile_id(rs.getLong("profile_id"));
		profile.setFirstName(rs.getString("first_name"));
		profile.setLastName(rs.getString("last_name"));
		profile.setPhoneNumber(rs.getString("phone_number"));
		profile.setMobileNumber(rs.getString("mobile_number"));
		
		Set<Authority> set = new HashSet<>();
		set.add(new Authority("ROLE_USER"));
		
		User user = new User();
		user.setUserId(rs.getLong("user_id"));
		user.setUsername(rs.getString("user_name"));
		user.setPassword(rs.getString("user_password"));
		user.setEnabled(rs.getBoolean("enabled"));
		user.setProfile(profile);
		user.setAuthorities(set);
		
		return user;
	}

}
