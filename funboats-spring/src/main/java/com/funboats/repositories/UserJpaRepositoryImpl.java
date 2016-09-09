package com.funboats.repositories;

import java.security.Principal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.funboats.model.SearchObject;
import com.funboats.model.User;
import com.funboats.repositories.maps.UserRowMapper;

@Repository
public class UserJpaRepositoryImpl implements  UserRepository{
	
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * inject DataSource into jdbcTemplate
	 * @param dataSource is the DB source
	 */
	@Autowired
	public UserJpaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	/**
	 * return total count of rows in users
	 * @return Long
	 */
    public Long getCount(){
    	String sql = "SELECT COUNT(*) FROM users";
    	return jdbcTemplate.queryForObject(sql, Long.class);
    }
    
    /**
     * return true if username is found in users
     * @param obj represents username
     * @return boolean
     */
    public boolean uniqueUserName(SearchObject obj){
    	String sql = "SELECT * FROM users u, profiles p, authorities a WHERE u.user_name = ?";
	           sql += " and u.user_id = p.profile_id";
	    
	    Object[] params = {obj.getSearch()};
	    
	    boolean found = false;
	    try{
    		found = jdbcTemplate.queryForObject(sql, params, new UserRowMapper()) != null; 			
    	}catch(Exception e){}
    	
    	return found;	 
    }
    
    /**
     * return User if username is found in users
     * @param username is the name of user
     * @return User
     */
    public User findByUserName(String username){

    	if (username == null)
			return null;
   	
    	String sql = "SELECT * FROM users u, profiles p, authorities a WHERE u.user_name = ?";
    	       sql += " and u.user_id = p.profile_id";
    	       
    	Object[] params = {username};
    	User user = jdbcTemplate.queryForObject(sql, params, new UserRowMapper());
    	
    	return user; 

    }

    /**
     * return true if save was succesful 
     * @params user object being saved
     * @return boolean
     */
    @Transactional
	public boolean saveAndFlush(User user) {

		int success = 0;
		String sql = "INSERT INTO profiles (first_name, last_name, phone_number, mobile_number) VALUES (?,?,?,?);";
			
		Object[] params = {
			user.getProfile().getFirstName(),
			user.getProfile().getLastName(),
			user.getProfile().getPhoneNumber(),
			user.getProfile().getMobileNumber()
		};	
		success = jdbcTemplate.update(sql, params);

		user.setPassword(User.encryptPassword(user.getPassword()));	
		
		sql = "INSERT INTO users (user_name, user_password, enabled, authority_id) VALUES (?,?,?,?);";
		params = new Object[]{
			user.getUsername(), 
			user.getPassword(), 
			user.getEnabled(), 1L
		};
			
		success = jdbcTemplate.update(sql, params);
			
		System.out.println("UserJpaRepositoryImpl :: saveAndFlush 3");
		return (success > 0);
	}
}

	
    


