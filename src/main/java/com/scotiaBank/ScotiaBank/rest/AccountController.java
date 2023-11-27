package com.scotiaBank.ScotiaBank.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Exception.CustomerNotFoundException;
import com.scotiaBank.ScotiaBank.Filter.JwtAuthenticationFilter;
import com.scotiaBank.ScotiaBank.Service.AccountHistoryService;
import com.scotiaBank.ScotiaBank.Service.AccountService;
import com.scotiaBank.ScotiaBank.Service.AuditLog;
import com.scotiaBank.ScotiaBank.dto.Account;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	 private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	AccountService accountService;
	@Autowired
	AccountHistoryService accountHistoryService;
	
	
	@GetMapping("/{accountId}")
	public ResponseEntity<String> getAccountDetails(@PathVariable long accountId)
	{
		Account result  = new Account();
		try {
			
		 result = accountService.getAccountDetails(accountId);
		 LOG.info("AccountController::getAccountDetails - getting account details were successfull for accountId "+accountId+" results"+result.toString());
		 AuditLog.auditLog(result.getUsername(), "getAccountDetails", System.currentTimeMillis(), "success", ""+accountId);
		 accountHistoryService.insertAccountLog(accountId,System.currentTimeMillis(),"getAccountDetails: successful");
		}
		catch(AccountNotFoundException userException) {
			accountHistoryService.insertAccountLog(accountId,System.currentTimeMillis(),"AccountNotFoundException");
	    	 LOG.error("AccountController::getAccountDetails - caught Exception :-AccountNotFoundException for accountId" +userException.getMessage());
	    	 AuditLog.auditLog("", "AccountNotFoundException", System.currentTimeMillis(), "failed", ""+accountId);
	    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
			   
		     
		}catch(AccessDeniedException Ae) {
			accountHistoryService.insertAccountLog(accountId,System.currentTimeMillis(),"AccessDeniedException");
			AuditLog.auditLog("AccountController::getAccountDetails", "AccessDeniedException", System.currentTimeMillis(), "failed", ""+accountId);
			LOG.error("AccountController::getAccountDetails - caught Exception :-AccessDeniedException for accountId" +Ae.getMessage());
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("only Admins or account holder  can see this account details");
		    
	     }catch(Exception e) {
	    	 accountHistoryService.insertAccountLog(accountId,System.currentTimeMillis(),"Failed");
	    	 AuditLog.auditLog("AccountController::getAccountDetails", "Exception", System.currentTimeMillis(), "failed", ""+accountId);
	    	 LOG.error("AccountController::getAccountDetails - caught Exception  for accountId" +e.getMessage());
	    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.ok(result.toString());
	}
	
	
	
	
	
	@PostMapping
	public ResponseEntity<String> addAccount(@RequestBody Account account)
	{ 
		Account result  = new Account();
		
		try {
		 result = accountService.addAccount(account);
		 
		 AuditLog.auditLog(result.getUsername(), "addAccount", System.currentTimeMillis(), "success", ""+account.toString());
		 accountHistoryService.insertAccountLog(result.getAccountID(),System.currentTimeMillis(),"Account Creation Success");
		 LOG.info("AccountController::addAccount - getting account details were successfull for accountId "+account.toString()+" results"+result.toString());
		}
		catch(CustomerNotFoundException userException) {
			accountHistoryService.insertAccountLog(result.getAccountID(),System.currentTimeMillis(),"Account Creation Failed:CustomerNotFoundException");
			AuditLog.auditLog("", "CustomerNotFoundException", System.currentTimeMillis(), "failed", ""+account.toString());
	    	 LOG.error("AccountController::addAccount - caught Exception :-CustomerNotFoundException for accountId" +userException.getMessage());
			   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
			   
		     
		}catch(AccessDeniedException Ae) {
			accountHistoryService.insertAccountLog(result.getAccountID(),System.currentTimeMillis(),"Account Creation Failed:AccessDeniedException");;
			AuditLog.auditLog("", "AccessDeniedException", System.currentTimeMillis(), "failed", ""+account.toString());
	    	 
			LOG.error("AccountController::addAccount - caught Exception :-AccessDeniedException for accountId" +Ae.getMessage());
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("only Admins or account holder  can see this account details");
		    
	     }catch(Exception e) {
	    	 accountHistoryService.insertAccountLog(result.getAccountID(),System.currentTimeMillis(),"Account Creation Failed");
	    	 AuditLog.auditLog("", "Exception", System.currentTimeMillis(), "failed", ""+account.toString());	    	 
	    	 LOG.error("AccountController::addAccount - caught Exception  for accountId" +e.getMessage());
	    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.ok(result.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Personal Methods 
	/*
	 * @PostMapping("/addAccvalues") public List<AccountEntity> addValue() {
	 * List<AccountEntity> list = new ArrayList<AccountEntity>(); Random random =
	 * new Random(); int randomNumber = random.nextInt(); for(int i =0 ; i<100; i++)
	 * { long acc_ID = random.nextInt(10)+i; String accNum = "ACC" +
	 * random.nextInt(10)+i; String accHolder = "ACC_Name"+i; Double balance =
	 * random.nextDouble()+i; long currentTimeMillis = System.currentTimeMillis();
	 * long randomMillis = (long) random.nextInt(365) * 24 * 60 * 60 * 1000; Date
	 * openDate = new Date(currentTimeMillis + randomMillis); Long cusID = (long) i;
	 * AccountEntity en = new AccountEntity( ); list.add(en);
	 * }
	 *  return accountService.addALL(list);
	 * }
	 * 
	 * @GetMapping("/getAccDetails") public Optional<AccountEntity> search() {
	 * return accountService.getAccDetails();
	 * 
	 * }
	 */
	

}
