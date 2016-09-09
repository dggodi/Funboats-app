package com.funboats.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;

import com.funboats.security.UserDetailsServiceImpl;

/**
 * class determines access to HTML resources
 */
@Component
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
		
	@Autowired 
	 private UserDetailsService userDetailsService;
	
	@Autowired 
	PasswordEncoder passwordencoder;
	
	/**
	 * injects UserDetailsServiceImpl
	 * @param userDetailsService for authentication methods
	 */
	public void setUserDetailsService(UserDetailsServiceImpl userDetailsService){
		this.userDetailsService = userDetailsService; 
	}
	
	/**
	 * injects BCryptPasswordEncoder
	 * @param passwordencoder for password decoding and encoding
	 */
	public void setPasswordEncoder(BCryptPasswordEncoder passwordencoder){
		this.passwordencoder = passwordencoder; 
	}
	
	/**
	 * enables the password encoder
	 * @param auth used for building in memory authentication
	 * @throws Exception if authentication fails
	 */
	@Autowired
	 public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {    
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder);
	 }
	
	
	/**
	 * Configures the HTML resources allowed 
	 * @param http allows configuring web based security for specific http requests
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic().and()
			.authorizeRequests()	
			.antMatchers("/fragments/public/**").permitAll()
			.antMatchers("/fragments/members/**").hasAnyAuthority("ROLE_USER")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginProcessingUrl("/authenticate")
			.permitAll()
	        .and()
		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordencoder(){
		return new BCryptPasswordEncoder();
	}
}	

