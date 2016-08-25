package com.funboats.controllers;
/*
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestParam;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
//import static org.springframework.web.bind.annotation.RequestMethod.POST;

import funboats.model.User;
import funboats.repositories.IUserJpaRepository;
*/

import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public void getByUserName(@RequestBody User user){
		System.out.println("\n\n### UserController : get my list \n\n");
		userRepository.authenticate(user.getUserName(), user.getPassword());
	}
				
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public void createUser(@RequestBody User user){
		System.out.println("\n\n### UserController : create \n\n");
		userRepository.saveAndFlush(user);
	}
}
