package com.purpledocs.boxtracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.purpledocs.boxtracker.entity.User;
import com.purpledocs.boxtracker.serviceImpl.UserDetailsService;

@Component
public class GetUser {

	@Autowired
	private  UserDetailsService userDetailService;
	
	
	public  User getUser()
	{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            if (userDetails instanceof User) {
                User user = (User) userDetails;
           
                return user;
               
             
            }
             return null;
        }
		return null; 
	}
	
}
