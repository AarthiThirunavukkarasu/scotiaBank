package com.scotiaBank.ScotiaBank.Service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.CustomerEntity;
import com.scotiaBank.ScotiaBank.Exception.CustomerAlreadyExistsException;
import com.scotiaBank.ScotiaBank.dto.Customer;
@Service
public interface CustomerService {
	
	public List<CustomerEntity> addALL(List<CustomerEntity> cus);
    
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Customer createCustomer(Customer customer) throws CustomerAlreadyExistsException;

	public CustomerEntity getCustomer();

}
