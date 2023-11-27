package com.scotiaBank.ScotiaBank.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Service.impl.AccountServiceImpl;
import com.scotiaBank.ScotiaBank.dto.Account;


@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class TestAccountController {

	@Mock	
	private AccountServiceImpl accountService;
	@InjectMocks
    private AccountController accountController;
	


	
	
	@Test
    public void testGetAccountDetailsSuccess() throws AccountNotFoundException, AccessDeniedException {
        long accountId = 1L;
        Account mockedAccount = new Account(); 

        when(accountService.getAccountDetails(accountId)).thenReturn(mockedAccount);

        ResponseEntity<String> response = accountController.getAccountDetails(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        
    }
	@Test
    public void testGetAccountDetailsAccountNotFoundException() throws AccountNotFoundException {
        long accountId = 2L;

        when(accountService.getAccountDetails(accountId)).thenThrow(new AccountNotFoundException("Account not found"));

        ResponseEntity<String> response = accountController.getAccountDetails(accountId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
    }

    @Test
    public void testGetAccountDetailsAccessDeniedException() throws AccessDeniedException {
        long accountId = 3L;

        when(accountService.getAccountDetails(accountId)).thenThrow(new AccessDeniedException("Access denied"));

        ResponseEntity<String> response = accountController.getAccountDetails(accountId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
    }

}
