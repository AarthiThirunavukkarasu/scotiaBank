package com.scotiaBank.ScotiaBank.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.scotiaBank.ScotiaBank.Service.impl.AuthenicateServiceImpl;
import com.scotiaBank.ScotiaBank.dto.JwtAuthenticationResponse;
import com.scotiaBank.ScotiaBank.dto.SignInRequest;
import com.scotiaBank.ScotiaBank.dto.SignUpRequest;
@RunWith(MockitoJUnitRunner.class)

@SpringBootTest
public class AuthenticationControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenicateServiceImpl authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignup_Success() {
        SignUpRequest signUpRequest = new SignUpRequest();
       

        JwtAuthenticationResponse mockedResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signup(any(SignUpRequest.class))).thenReturn(mockedResponse);

      JwtAuthenticationResponse response = authenticationController.signup(signUpRequest);

       // assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(mockedResponse, response.getBody());
        
    }

    @Test
    public void testSignin_Success() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
       

        JwtAuthenticationResponse mockedResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signin(any(SignInRequest.class))).thenReturn(mockedResponse);

        JwtAuthenticationResponse response = authenticationController.signin(signInRequest);

		/*
		 * assertEquals(HttpStatus.OK, response.getStatusCode());
		 * assertEquals(mockedResponse, response.getBody());
		 */
        
    }
}
