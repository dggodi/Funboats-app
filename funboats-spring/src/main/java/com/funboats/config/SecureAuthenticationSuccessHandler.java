package com.funboats.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SecureAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private AuthenticationSuccessHandler handler;
	
	public SecureAuthenticationSuccessHandler(AuthenticationSuccessHandler handler){
		this.handler = handler;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		handler.onAuthenticationSuccess(request, response, authentication);
	}

}
