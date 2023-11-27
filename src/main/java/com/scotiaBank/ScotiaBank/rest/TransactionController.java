package com.scotiaBank.ScotiaBank.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Exception.TransactionFailedException;
import com.scotiaBank.ScotiaBank.Service.AccountService;
import com.scotiaBank.ScotiaBank.Service.AuditLog;
import com.scotiaBank.ScotiaBank.Service.TransactionService;
import com.scotiaBank.ScotiaBank.dto.Account;
import com.scotiaBank.ScotiaBank.dto.Transactions;

@RestController
@RequestMapping("/api/transcations")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	@Autowired
	AccountService accountService;
	private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
	@PostMapping
	public ResponseEntity<String> performTransaction (@RequestBody Transactions transaction) {
		Transactions result  = new Transactions();
		try {
			//checkValidUser(transaction,transaction.getUsername());
			if(transaction.getAmount()>=1){
				result = transactionService.performTransaction(transaction, transaction.getUsername());
				AuditLog.auditLog(result.toString(), "performTransaction", System.currentTimeMillis(), "success", ""+transaction.toString());
				
				LOG.info("TransactionController::performTransaction - updating transaction details  were successfull "+" results"+result.toString());
				//audit logs
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TransactionFailed : invalid amount");
			}
		 
		}
		catch(AccountNotFoundException userException) {
	    	 
			   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
			   
		     
		}
		catch(TransactionFailedException te) {
			AuditLog.auditLog("TransactionController::performTransaction", "TransactionFailedException", System.currentTimeMillis(), "failed", ""+transaction.toString());
		    
			 LOG.error("TransactionController::performTransaction - caught Exception :-TransactionFailedException for accountId" +te.getMessage());
			   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(te.getMessage());

		}catch(AccessDeniedException Ae) {
			AuditLog.auditLog("TransactionController::performTransaction", "TransactionFailedException", System.currentTimeMillis(), "failed", ""+transaction.toString());
		    
			 LOG.error("TransactionController::performTransaction - caught Exception :-AccessDeniedException for accountId" +Ae.getMessage());
	    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("transaction is not allowed by the user");
	     }
		catch(Exception e) {
			AuditLog.auditLog("TransactionController::performTransaction", "Exception", System.currentTimeMillis(), "failed", ""+transaction.toString());
		    
	    	 LOG.error("TransactionController::performTransaction - caught Exception " +e.getMessage());
	    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.ok(result.toString());

		//return transactionService.performTransaction(transaction);
	}
	/*
	 * @PreAuthorize("#username == authentication.principal.username or hasRole('ROLE_ADMIN')"
	 * )
	 * 
	 * @PostAuthorize(return) private Account checkValidUser(Transactions
	 * transaction, String username) throws Exception{ Account acc =
	 * accountService.getAccountDetails(transaction.getAccountID());
	 * 
	 * return acc;
	 * 
	 * }
	 */
	

	
	
	
	
	// Personal Methods 
	/*
	 * @PostMapping("addTransValues") public List<TransactionEntity> addValue() {
	 * List<TransactionEntity> list = new ArrayList<TransactionEntity>(); Random
	 * random = new Random(); for(int i =200 ; i<300; i++) {
	 * 
	 * long trans_ID = random.nextInt(100); System.out.println(" iD"+trans_ID);
	 * String transactionType = "deposit"; Double amount = random.nextDouble(5)+i;
	 * LocalDateTime currentDateTime = LocalDateTime.now(); Date
	 * transactionTimeStamp = getISODate(); Long accountID = (long) i;
	 * 
	 * 
	 * TransactionEntity en = new TransactionEntity(); list.add(en);
	 * System.out.println(i); }
	 * 
	 * return transactionService.addALL(list); }
	 */
	
	/*
	 * public Date getISODate() { // Get the current date and time Date currentDate
	 * = new Date(); Date parsedDate = new Date();
	 * 
	 * // Format the date to ISO 8601 SimpleDateFormat iso8601Format = new
	 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); String iso8601Date =
	 * iso8601Format.format(currentDate);
	 * 
	 * //System.out.println("Formatted Date: " + iso8601Date);
	 * 
	 * try { // Parse the ISO 8601 formatted string back to a Date parsedDate =
	 * iso8601Format.parse(iso8601Date);
	 * 
	 * System.out.println("Parsed Date: " + parsedDate); } catch (ParseException e)
	 * { e.printStackTrace(); } return parsedDate; }
	 */
	 
	 
	 
		
	}




