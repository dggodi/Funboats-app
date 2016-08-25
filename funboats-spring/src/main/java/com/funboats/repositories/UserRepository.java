package com.funboats.repositories;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.funboats.model.User;

@Repository
public interface UserRepository{
	ResponseEntity<User> saveAndFlush(User user);
	User authenticate(String userName, String password);
	User findByUserName(String username);	
}