package com.funboats.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *	class authenticates spring security session
 */
public final class SecurityUtil {
	
	/**
	 * return username if session name matches current login
	 * @return String
	 */
	public static String getCurrentLogin(){
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails securityUser = null;
		String username = null;
		
		if (authentication != null){		
			if(authentication.getPrincipal() instanceof UserDetails){
				securityUser = (UserDetails)authentication.getPrincipal();
				username = securityUser.getUsername();
			}
			else if (authentication.getPrincipal() instanceof String){
				username = (String) authentication.getPrincipal();
			}
		}

		return username;
	}
}
