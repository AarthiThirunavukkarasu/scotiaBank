package com.scotiaBank.ScotiaBank.rest;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import org.springframework.security.access.AccessDeniedException;

import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Exception.TransactionFailedException;
import com.scotiaBank.ScotiaBank.Service.TransactionService;
import com.scotiaBank.ScotiaBank.dto.Transactions;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestTranscationController {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPerformTransaction_Success() throws AccountNotFoundException, TransactionFailedException, AccessDeniedException {
        Transactions mockedTransaction = new Transactions();
        mockedTransaction.setAmount(10.0); // Set a valid amount for a successful transaction

        when(transactionService.performTransaction(any(Transactions.class), any(String.class))).thenReturn(mockedTransaction);

        ResponseEntity<String> response = transactionController.performTransaction(mockedTransaction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions based on your requirements
    }

    @Test
    public void testPerformTransaction_AccountNotFoundException() throws AccountNotFoundException, TransactionFailedException, AccessDeniedException {
        Transactions mockedTransaction = new Transactions();
        mockedTransaction.setAmount(10.0);

        when(transactionService.performTransaction(any(Transactions.class), any(String.class))).thenThrow(new AccountNotFoundException("Account not found"));

        ResponseEntity<String> response = transactionController.performTransaction(mockedTransaction);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add more assertions based on your requirements
    }

    @Test
    public void testPerformTransaction_TransactionFailedException() throws AccountNotFoundException, TransactionFailedException, AccessDeniedException {
        Transactions mockedTransaction = new Transactions();
        mockedTransaction.setAmount(0.0); // Set an invalid amount for a failed transaction

        when(transactionService.performTransaction(any(Transactions.class), any(String.class))).thenThrow(new TransactionFailedException("Transaction failed"));

        ResponseEntity<String> response = transactionController.performTransaction(mockedTransaction);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add more assertions based on your requirements
    }

    @Test
    public void testPerformTransaction_AccessDeniedException() throws AccountNotFoundException, TransactionFailedException, AccessDeniedException {
        Transactions mockedTransaction = new Transactions();
        mockedTransaction.setAmount(10.0);

        when(transactionService.performTransaction(any(Transactions.class), any(String.class))).thenThrow(new AccessDeniedException("Access denied"));

        ResponseEntity<String> response = transactionController.performTransaction(mockedTransaction);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add more assertions based on your requirements
    }

    @Test
    public void testPerformTransaction_Exception() throws AccountNotFoundException, TransactionFailedException, AccessDeniedException {
        Transactions mockedTransaction = new Transactions();
        mockedTransaction.setAmount(10.0);

        when(transactionService.performTransaction(any(Transactions.class), any(String.class))).thenThrow(new RuntimeException("An unexpected exception occurred"));

        ResponseEntity<String> response = transactionController.performTransaction(mockedTransaction);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add more assertions based on your requirements
    }
}
