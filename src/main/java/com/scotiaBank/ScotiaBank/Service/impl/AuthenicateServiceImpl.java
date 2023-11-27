package com.scotiaBank.ScotiaBank.Service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.ERole;
import com.scotiaBank.ScotiaBank.Entity.User;
import com.scotiaBank.ScotiaBank.dto.JwtAuthenticationResponse;
import com.scotiaBank.ScotiaBank.dto.SignInRequest;
import com.scotiaBank.ScotiaBank.dto.SignUpRequest;
import com.scotiaBank.ScotiaBank.repositories.UserRepository;
import com.scotiaBank.ScotiaBank.security.JwtProvider;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenicateServiceImpl {
	
	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	CustomUserDetailsServiceImpl userService;
    @Autowired
	PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtService;
    @Autowired
	AuthenticationManager authenticationManager;
    private static final Logger LOG = LoggerFactory.getLogger(AuthenicateServiceImpl.class);
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User user = User
                    .builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(ERole.valueOf(request.getRole()))
                    .build();
        LOG.info("AuthenicateServiceImpl::signup "+request.getUsername());
        //audit log
        user = userService.createNewUser(user);
        var jwt = jwtService.generateTokenUser(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
    
    public JwtAuthenticationResponse signin(SignInRequest request) throws Exception{
    	LOG.info(" JwtAuthenticationResponse::signin "+request.toString());
    	try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        LOG.info("AuthenicateServiceImpl::signin "+request.getUsername());
    	}catch(Exception e) {
    		LOG.error("JwtAuthenticationResponse :: signin caught exception"+e.getMessage());
    	}
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        LOG.info(" JwtAuthenticationResponse::signin "+user);
        LOG.info(" JwtAuthenticationResponse::signin Auth completed");
        String jwt = jwtService.generateTokenUser(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
