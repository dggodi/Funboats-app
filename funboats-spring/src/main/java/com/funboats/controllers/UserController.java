package com.funboats.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.funboats.model.SearchObject;
import com.funboats.model.User;
import com.funboats.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/users")
public class UserController{
		
	@Autowired
	private UserRepository userRepository;
			
	/**
	 * return true if save was succesful
	 * @param user is the user object to be mapped to the DB
	 * @return boolean
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public boolean create(@RequestBody User user){
		System.out.println("UserController :: create");
		return userRepository.saveAndFlush(user);
	}

	/**
	 * return true if username is in DB
	 * @param username 
	 * @return boolean
	 */
	@RequestMapping(value="/username", method = RequestMethod.POST)
	public boolean get(@RequestBody SearchObject username){		
		return userRepository.uniqueUserName(username);
	}
}
