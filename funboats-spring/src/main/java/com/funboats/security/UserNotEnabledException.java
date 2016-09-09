package com.funboats.security;

import org.springframework.security.core.AuthenticationException;

/**
 * exception class is user has not been enabled
 */
public class UserNotEnabledException extends AuthenticationException {

	private static final long serialVersionUID = -8062269812294181703L;

	public UserNotEnabledException(String msg) {
		super(msg);

	}
	
	public UserNotEnabledException(String msg, Throwable t) {
		super(msg, t);
	}
}
