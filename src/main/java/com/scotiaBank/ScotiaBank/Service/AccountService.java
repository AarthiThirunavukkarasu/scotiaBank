package com.scotiaBank.ScotiaBank.Service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Exception.CustomerNotFoundException;
import com.scotiaBank.ScotiaBank.dto.Account;
@Service
public interface AccountService {
	
	@PostAuthorize("returnObject.username == authentication.principal.username or hasRole('ROLE_ADMIN')")
	public Account getAccountDetails(long accountId) throws AccountNotFoundException;

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Account addAccount(Account account) throws CustomerNotFoundException;

	//public void checkUserhasAccess(String username);

}
