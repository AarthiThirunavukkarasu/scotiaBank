package com.scotiaBank.ScotiaBank.Service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.TransactionEntity;
import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Exception.TransactionFailedException;
import com.scotiaBank.ScotiaBank.dto.Transactions;

@Service
public interface TransactionService {
	
	public List<TransactionEntity> addALL(List<TransactionEntity> transEN);
	
	@PostAuthorize("returnObject.username == authentication.principal.username or hasRole('ROLE_ADMIN')")
	public Transactions performTransaction(Transactions transaction, String username) throws TransactionFailedException,AccountNotFoundException;
	
	//@PostFilter("filterObject.username.equalsIgnoreCase(authentication.principal.username)")
	public List<Transactions> getStatements(long accountId);

	

}
