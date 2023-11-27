package com.scotiaBank.ScotiaBank.Service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.dto.Customer;
@Service
public interface CustomerServiceValidator {
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	boolean validateCustomerInput(Customer customer);

}
