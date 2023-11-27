package com.scotiaBank.ScotiaBank.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import com.scotiaBank.ScotiaBank.Exception.CustomerAlreadyExistsException;
import com.scotiaBank.ScotiaBank.Exception.CustomerInputNotValidException;
import com.scotiaBank.ScotiaBank.Service.CustomerService;
import com.scotiaBank.ScotiaBank.Service.CustomerServiceValidator;
import com.scotiaBank.ScotiaBank.dto.Customer;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestCustomerController {

	 @Mock
	    private CustomerService customerService;

	    @Mock
	    private CustomerServiceValidator customerServiceValidator;

	    @InjectMocks
	    private CustomerController customerController;



	    @Test
	    public void testCreateCustomer_Success() throws CustomerInputNotValidException, CustomerAlreadyExistsException, AccessDeniedException {
	        Customer mockedCustomer = new Customer();

	        when(customerServiceValidator.validateCustomerInput(any(Customer.class))).thenReturn(true);
	        when(customerService.createCustomer(any(Customer.class))).thenReturn(mockedCustomer);

	        ResponseEntity<String> response = customerController.createCustomer(mockedCustomer);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        
	    }

	    @Test
	    public void testCreateCustomer_CustomerInputNotValidException() throws CustomerInputNotValidException {
	        Customer mockedCustomer = new Customer();

	        when(customerServiceValidator.validateCustomerInput(any(Customer.class))).thenReturn(false);

	        ResponseEntity<String> response = customerController.createCustomer(mockedCustomer);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        
	    }

	    @Test
	    public void testCreateCustomer_CustomerAlreadyExistsException() throws CustomerInputNotValidException, CustomerAlreadyExistsException {
	        Customer mockedCustomer = new Customer();

	        when(customerServiceValidator.validateCustomerInput(any(Customer.class))).thenReturn(true);
	        when(customerService.createCustomer(any(Customer.class))).thenThrow(new CustomerAlreadyExistsException("Customer already exists"));

	        ResponseEntity<String> response = customerController.createCustomer(mockedCustomer);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        
	    }

	    @Test
	    public void testCreateCustomer_AccessDeniedException() throws CustomerInputNotValidException, AccessDeniedException {
	        Customer mockedCustomer = new Customer();

	        when(customerServiceValidator.validateCustomerInput(any(Customer.class))).thenReturn(true);
	        when(customerService.createCustomer(any(Customer.class))).thenThrow(new AccessDeniedException("Access denied"));

	        ResponseEntity<String> response = customerController.createCustomer(mockedCustomer);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        
	    }
	}

