package com.funboats.repositories;

import org.springframework.stereotype.Repository;

import com.funboats.model.SearchObject;
import com.funboats.model.User;

@Repository
public interface UserRepository{
	boolean saveAndFlush(User user);
	User findByUserName(String username);	
	boolean uniqueUserName(SearchObject obj);
}