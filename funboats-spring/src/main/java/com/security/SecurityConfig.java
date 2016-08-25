package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.funboats.config.SecureAuthenticationSuccessHandler;
import com.funboats.model.User;
import com.funboats.repositories.UserLoginService;
import com.funboats.repositories.UserRepository;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//public class SecurityConfig  extends WebSecurityConfigurerAdapter implements UserLoginService{

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter implements UserLoginService{
		
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()		
			.antMatchers("resources/public**").permitAll()
			.antMatchers("resources/members**").hasRole("MEMBER")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/authenticate")
			.usernameParameter("username")
			.passwordParameter("password")
			.successHandler(new SecureAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
			.and()
		.httpBasic()
			.and()
		.logout()
			.logoutSuccessUrl("resources/fragments/public/login?logout")
			.permitAll()
			.and()
		.csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());;
	}	
}	
	/*
		http
        .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
        .logout()
            .permitAll();
		*/
		
		/*
		http.authorizeRequests()
		.antMatchers("/members/**").access("hasRole('USER')")
			.anyRequest().permitAll()
			.and()
				.formLogin().loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
			.and()
				.logout().logoutSuccessUrl("/login?logout")
			.and()
				.exceptionHandling().accessDeniedPage("/403")
			.and()	
				.csrf();
				*/
	


	
	/*
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password("password").roles("USER");
    }
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
	}
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordencoder(){
		return new BCryptPasswordEncoder();
	}*/

