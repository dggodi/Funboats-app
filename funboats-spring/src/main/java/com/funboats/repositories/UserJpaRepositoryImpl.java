package com.funboats.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.funboats.model.User;

@Controller
public class UserJpaRepositoryImpl implements  UserRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	 
    public UserJpaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public User findByUserName(String username) {
		String sql = "SELECT user_name FROM users WHERE user_name = ?";
		Object[] params = {username};
		List<User> user = jdbcTemplate.query(sql,  params, new RowMapper<User>(){

			public User mapRow(ResultSet rs, int row) throws SQLException {
				User user = new User();
				user.setUserName(rs.getNString("user_name"));
				return null;
			}
		});
		return user.get(0);
	}

	public ResponseEntity<User> saveAndFlush(User user) {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
		ResponseEntity<User> responseEntity;
		
		try{
			String sqlprofile = "INSERT INTO profiles (first_name, last_name, phone_number, mobile_number) VALUES (?,?,?,?);";
			String sqluser = "INSERT INTO profiles (user_name, user_password, user_role) VALUES (?,?,?);";
			
			Object[] params = {
				user.getProfile().getFirstName(),
				user.getProfile().getLastName(),
				user.getProfile().getPhoneNumber(),
				user.getProfile().getMobileNumber()
			};
			
			int ck = jdbcTemplate.update(sqlprofile, params);
			System.out.println("UserJpaRepositoryImpl :: saveAndFlush profile   " + ck);
			
			params = new Object[]{
				user.getUserName(), user.getPassword(), user.getRole()
			};
			
			int ck1 = jdbcTemplate.update(sqluser, params);
			
			System.out.println("UserJpaRepositoryImpl :: saveAndFlush user    " + ck1);
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
			transactionManager.commit(status);
			
		}catch (Exception e){
			transactionManager.rollback(status);
			responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		return responseEntity;	
	}
	
	public User authenticate(String userName, String password){
		String sql = "select username from users where user_name=? and user_password=?";
		Object[] params = {userName, password};
		List<User> user = jdbcTemplate.query(sql, params, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException{
				User user = new User();
				user.setUserName(rs.getString("user_name"));
				user.setUserName(rs.getString("user_password"));
				return user;
			}
		});
		return user.get(0);
	}

}

	
    


