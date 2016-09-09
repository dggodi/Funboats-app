package com.funboats.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.funboats.model.User;
import com.funboats.repositories.UserRepository;
import com.funboats.security.SecurityUtil;

@RestController

public class SecurityController {
	
	@Autowired
	private UserRepository userRespository;
	
	/**
	 * return User object if stored session username is in the DB
	 * @return User
	 */
	@RequestMapping(value = "/security/account", method = RequestMethod.GET)
	public @ResponseBody User getUserAccount(){

		User user = userRespository.findByUserName(SecurityUtil.getCurrentLogin());	
		if(user != null)user.setPassword(null);
		
		return user;
	}

	/**
	 * Used 
	 * @param user represents the identity used to determine access rights
	 * @return Principal
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public Principal login(Principal user)
	{
		return user;
	}

}
