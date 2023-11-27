package com.scotiaBank.ScotiaBank.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Service.TransactionService;
import com.scotiaBank.ScotiaBank.dto.Transactions;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestStatementController {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private StatementController statementController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPerformTransaction_Success() throws AccountNotFoundException {
        long accountId = 1L;
        List<Transactions> mockedTransactions = new ArrayList<>();

        when(transactionService.getStatements(accountId)).thenReturn(mockedTransactions);

        ResponseEntity<String> response = statementController.performTransaction(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions based on your requirements
    }

    @Test
    public void testPerformTransaction_AccountNotFoundException() throws AccountNotFoundException {
        long accountId = 2L;

        when(transactionService.getStatements(accountId)).thenThrow(new AccountNotFoundException("Account not found"));

        ResponseEntity<String> response = statementController.performTransaction(accountId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add more assertions based on your requirements
    }

    @Test
    public void testPerformTransaction_Exception() throws AccountNotFoundException {
        long accountId = 3L;

        when(transactionService.getStatements(accountId)).thenThrow(new RuntimeException("An unexpected exception occurred"));

        ResponseEntity<String> response = statementController.performTransaction(accountId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add more assertions based on your requirements
    }
}
