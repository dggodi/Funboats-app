package com.funboats.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.funboats.model.User;
import com.funboats.repositories.UserRepository;

public class SecureUserDetailsService implements UserDetailsService{
	
	static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("SecureUserDetailsService :: loadUserByUsername");;
		User user = userRepository.findByUserName(username);
		
		if(user == null){
			String mes = "Your email or password does not match";
			throw new UsernameNotFoundException(mes);
		}
		
		String encodedpassword = passwordEncoder.encode(user.getPassword());
		
		List<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority("ROLE_USER"));
			
		
		return new org.springframework.security.core.userdetails.User(username, encodedpassword, auth);
	}
}
