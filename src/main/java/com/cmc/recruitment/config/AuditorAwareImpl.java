package com.cmc.recruitment.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
	  
    @Override
    public String getCurrentAuditor() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication == null ? "null" : authentication.getName();
		return username;
    }
 
}
