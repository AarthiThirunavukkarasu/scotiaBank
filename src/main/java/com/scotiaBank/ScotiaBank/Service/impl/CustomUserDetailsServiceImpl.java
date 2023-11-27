package com.scotiaBank.ScotiaBank.Service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.User;
import com.scotiaBank.ScotiaBank.repositories.UserRepository;

	
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService{
	 private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);
	public CustomUserDetailsServiceImpl() {
		
	}
	@Autowired
	UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
          LOG.info(" user loadUserByUsername"+user.toString());
         
        return user;
        		/*new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );*/
    }
    
 

	public User createNewUser(User newUser) {
    	if(newUser.getId()==null) {
    		newUser.setCreatedAt(LocalDateTime.now());
    	}
    	//AUDIT LOGS
    	newUser.setUpdateAt(LocalDateTime.now());
    	return userRepository.save(newUser);
		
	}
}