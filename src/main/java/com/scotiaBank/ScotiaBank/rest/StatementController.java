package com.scotiaBank.ScotiaBank.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Service.AuditLog;
import com.scotiaBank.ScotiaBank.Service.TransactionService;
import com.scotiaBank.ScotiaBank.dto.Transactions;

@RestController
@RequestMapping("/api/statements/")
public class StatementController {
	@Autowired
	TransactionService transactionService;
	private static final Logger LOG = LoggerFactory.getLogger(StatementController.class);
	@GetMapping("{accountId}")
	public ResponseEntity<String> performTransaction(@PathVariable long accountId)
	{
		List<Transactions> result  = new ArrayList<Transactions>();
		try {
			//auditlogs
		 result = transactionService.getStatements(accountId);
		 AuditLog.auditLog(result.toString(), "createCustomer", System.currentTimeMillis(), "success", ""+accountId);
			
		 LOG.info("StatementController::performTransaction - getting transaction details details were successfull for accountId "+accountId+" results"+result.toString());
		}
		catch(AccountNotFoundException userException) {
			AuditLog.auditLog("StatementController::performTransaction", "AccountNotFoundException", System.currentTimeMillis(), "failed", ""+accountId);
		    
			LOG.error("StatementController::performTransaction - caught Exception :-AccountNotFoundException for accountId" +userException.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
			   
		     
		}catch(Exception e) {
			AuditLog.auditLog("StatementController::performTransaction", "Exception", System.currentTimeMillis(), "failed", ""+accountId);
		    
			LOG.error("StatementController::performTransaction - caught Exception :" +e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.ok(result.toString());
	}
	
}
