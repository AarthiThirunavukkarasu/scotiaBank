package com.scotiaBank.ScotiaBank.Service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Exception.CustomerInputNotValidException;
import com.scotiaBank.ScotiaBank.Service.CustomerServiceValidator;
import com.scotiaBank.ScotiaBank.dto.Customer;

@Service
public class CustomerServiceValidatorImpl  implements CustomerServiceValidator{

	@Override
	public boolean validateCustomerInput(Customer customer) throws CustomerInputNotValidException {
		
		if(!isValidEmailID(customer.getEmailId())) {
			throw new CustomerInputNotValidException("Given " + customer.getEmailId() + " Not a valid Email ID.");
		}
		/*if(!isValidPhoneNumber(customer.getPhoneNumber())) {
			throw new CustomerInputNotValidException("Given " + customer.getPhoneNumber() + " Not a valid phone number.");
		}*/
		if(!isValidSin(customer.getSin())) {
			
			throw new CustomerInputNotValidException("Given " + customer.getSin() + " Not a valid SIN number.");
		}
		
		/*if(!isValidPostalCode(customer.getPostalCode())) {
			
			throw new CustomerInputNotValidException("Given " + customer.getSin() + " Not a valid Postal number.");
		}*/
		return true;
	}

	private boolean isValidEmailID(String emailId) {
    	String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
		
	}

	
    

    private boolean isValidPhoneNumber(String phoneNumber) {
    	String PHONE_NUMBER_REGEX = "^\\+\\d{1,3}[-.\\s]?\\(\\d{1,4}\\)[-\\s]?\\d{1,10}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    
    


    private static boolean isValidSin(String sin) {
       String SIN_REGEX = "^\\d{3}-\\d{3}-\\d{3}$";
       Pattern pattern = Pattern.compile(SIN_REGEX);
        Matcher matcher = pattern.matcher(sin);
        return matcher.matches();
    }
    
    


     private  boolean isValidPostalCode(String postalCode) {
        String POSTAL_CODE_REGEX = "^[A-Za-z0-9]{5}(?:-[A-Za-z0-9]{4})?$";
        Pattern pattern = Pattern.compile(POSTAL_CODE_REGEX);
        Matcher matcher = pattern.matcher(postalCode);
        return matcher.matches();
    }
}
