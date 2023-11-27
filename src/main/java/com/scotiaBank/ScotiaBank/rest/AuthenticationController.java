package com.scotiaBank.ScotiaBank.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotiaBank.ScotiaBank.Service.UserLogService;
import com.scotiaBank.ScotiaBank.Service.impl.AuthenicateServiceImpl;
import com.scotiaBank.ScotiaBank.dto.JwtAuthenticationResponse;
import com.scotiaBank.ScotiaBank.dto.SignInRequest;
import com.scotiaBank.ScotiaBank.dto.SignUpRequest;
import com.scotiaBank.ScotiaBank.security.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/")

public class AuthenticationController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
     @Autowired
     AuthenicateServiceImpl authenticationService;
 	@Autowired
 	UserLogService userLogService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest request) {
    	LOG.info("AuthenticationController::signup -  "+request.toString());
    	//audit logs
    	userLogService.insertUserLog(request.getUsername(),System.currentTimeMillis(),"Sign UP");
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) throws Exception {
    	LOG.info("AuthenticationController::signin -  "+request.toString());
    	userLogService.insertUserLog(request.getUsername(),System.currentTimeMillis(),"Log in ");
        return authenticationService.signin(request);
    }
}