package com.funboats.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.funboats.model.Authority;
import com.funboats.model.User;
import com.funboats.repositories.UserRepository;

/**
 * class that provides a service for creating spring security session
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * return UserDetails if session authority is granted
	 * @param login is the login name to authenticate
	 * @return UserDetails
	 * 
	 * note: retrieve user from DB and store user's details and their authority
	 */
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
	
		User user = userRepository.findByUserName(login);
	
		if(user == null)
			throw new UsernameNotFoundException("Username is incorrect");
		else if (!user.getEnabled())
			throw new UserNotEnabledException("User " + login + "was not enabled");
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(Authority authority : user.getAuthorities()){
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getRole());
			grantedAuthorities.add(grantedAuthority); 
		}

		return new org.springframework.security.core.userdetails.User(login, user.getPassword(), grantedAuthorities);
	}
}
