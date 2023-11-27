package com.scotiaBank.ScotiaBank.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotiaBank.ScotiaBank.Exception.CustomerAlreadyExistsException;
import com.scotiaBank.ScotiaBank.Exception.CustomerInputNotValidException;
import com.scotiaBank.ScotiaBank.Service.AuditLog;
import com.scotiaBank.ScotiaBank.Service.CustomerService;
import com.scotiaBank.ScotiaBank.Service.CustomerServiceValidator;
import com.scotiaBank.ScotiaBank.dto.Customer;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerServiceValidator customerServiceValidator;
	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
	@PostMapping
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
		Customer result = new Customer();
	   try {	   
		   LOG.info("CustomerController::createCustomer - creating new customer"+result.toString());
			//audit logs
		   Boolean validInput= customerServiceValidator.validateCustomerInput(customer);
		   if(validInput) {
			   result =  customerService.createCustomer(customer);
			   AuditLog.auditLog(result.getFirstName(), "createCustomer", System.currentTimeMillis(), "success", ""+customer.toString());
				
		   }
		   
	     }catch(CustomerInputNotValidException inputException) {
	    	 
	    	 AuditLog.auditLog("CustomerController::createCustomer", "CustomerInputNotValidException", System.currentTimeMillis(), "failed", ""+customer.toString());
		    	 
	     LOG.error("CustomerController::createCustomer - caught Exception :-CustomerInputNotValidException " +inputException.getMessage());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputException.getMessage());
		   
	     }catch(CustomerAlreadyExistsException userException) {
	    	 AuditLog.auditLog("CustomerController::createCustomer", "CustomerAlreadyExistsException", System.currentTimeMillis(), "failed", ""+customer.toString());
	      LOG.error("CustomerController::createCustomer - caught Exception :-CustomerAlreadyExistsException " +userException.getMessage());
		  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
		   
	     }catch(AccessDeniedException Ae) {
	    	 AuditLog.auditLog("CustomerController::createCustomer", "AccessDeniedException", System.currentTimeMillis(), "failed", ""+customer.toString());
	    	 LOG.error("AccessDeniedException::createCustomer - caught Exception :-AccessDeniedException " +Ae.getMessage());
	    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("only Admins can edit");
	     }
	   catch(Exception e) {
		   AuditLog.auditLog("CustomerController::createCustomer", "Exception", System.currentTimeMillis(), "failed", ""+customer.toString());
		   LOG.error("CustomerController::createCustomer - caught Exception : " +e.getMessage());
		   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	     }
	   
		return ResponseEntity.ok(result.toString());
	}
	
	
	
	
	
	
	/*@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return new ResponseEntity("You gave an incorrect value for ....", HttpStatus.BAD_REQUEST);
    }*/

	
	
	
	// Personal Methods  
	
	/*
	 * @PostMapping("getCustomers") public CustomerEntity getCustomer() {
	 * 
	 * //System.out.println("customer "+ cus.toString()); return
	 * customerService.getCustomer(); }
	 * 
	 * @PostMapping("addvalues") public List<CustomerEntity> addValue() {
	 * List<CustomerEntity> list = new ArrayList<CustomerEntity>(); for(int i =0 ;
	 * i<100; i++) { String first = "Customer_First"+i; String last =
	 * "Customer_last"+i; String email = "Customer_email"+i+"@hotmail.com";
	 * 
	 * CustomerEntity en = new CustomerEntity( ); list.add(en);
	 * 
	 * }
	 * 
	 * return customerService.addALL(list);
	 * 
	 * 
	 * 
	 * }
	 */

}
