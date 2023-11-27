package com.scotiaBank.ScotiaBank.Service.impl;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.AccountEntity;
import com.scotiaBank.ScotiaBank.Entity.CustomerEntity;
import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Exception.CustomerNotFoundException;
import com.scotiaBank.ScotiaBank.Service.AccountService;
import com.scotiaBank.ScotiaBank.Service.EncryptBean;
import com.scotiaBank.ScotiaBank.Service.MaskBeanService;
import com.scotiaBank.ScotiaBank.dto.Account;
import com.scotiaBank.ScotiaBank.repositories.AccountRepository;
import com.scotiaBank.ScotiaBank.repositories.CustomerRepository;
import com.scotiaBank.ScotiaBank.rest.TransactionController;

@Service
public class AccountServiceImpl  implements AccountService{
	
	
	@Autowired
	AccountRepository accountRepositories;
	
	@Autowired
	CustomerRepository customerRepository;

	private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Override
	public Account getAccountDetails(long accountId) throws AccountNotFoundException{
		// TODO Auto-generated method stub
		LOG.info("AccountServiceImpl::getAccountDetails ");
		AccountEntity accountEntity = new AccountEntity();
		Account resultAccount = new Account();
		try {
			accountEntity = accountRepositories.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));
			resultAccount= setDtoParameter(accountEntity);
		}catch(AccountNotFoundException ce) {
			LOG.error("AccountServiceImpl::getAccountDetails"+ce.getMessage());
			 throw new AccountNotFoundException(ce.getMessage());
		}
		catch (Exception e) {
			LOG.error("AccountServiceImpl::getAccountDetails"+e.getMessage());
			
		}
		return resultAccount;
	}

	@Override
	public Account addAccount(Account account) throws CustomerNotFoundException {
		AccountEntity accountEntity = new AccountEntity();
		AccountEntity resultAccountEntity = new AccountEntity();
		Optional<CustomerEntity> customerEntity = Optional.ofNullable(new CustomerEntity());
		Account resultAccount = new Account();
		try {
			LOG.info("AccountServiceImpl::addAccount "+customerRepository.existsById(account.getCustomerID()));
			// Check if a customer with the ID exists
	        if (!customerRepository.existsById(account.getCustomerID())) {
	        	LOG.info("AccountServiceImpl::addAccount"+account.getCustomerID());
	        	throw new CustomerNotFoundException("Customer not found with id: " + account.getCustomerID());
	        }
		//customerEntity = customerRepository.findById((long) account.getCustomerID());
                //.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + account.getCustomerID()));
	    customerEntity=  customerRepository.findById(account.getCustomerID());
		accountEntity = createEntityParameter( account, customerEntity);
		resultAccountEntity = accountRepositories.save(accountEntity);
		resultAccount= setDtoParameter(resultAccountEntity);
		
		}catch(CustomerNotFoundException ce) {
			 throw new CustomerNotFoundException("Customer not found with id: " + account.getCustomerID());
		}catch(NoSuchElementException ne) {
			LOG.error("AccountServiceImpl::addAccount"+"service impl"+ne);
			throw new CustomerNotFoundException("Customer not found with id: " + account.getCustomerID());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return resultAccount;
	}

	private AccountEntity createEntityParameter(Account account, Optional<CustomerEntity> customerEntity) throws Exception {
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setACCOUNT_ID(uniqueNumberGenerator());
		accountEntity.setACCOUNTNUMBER(EncryptBean.encrypt(uniqueAccNumberGenerator()));
		accountEntity.setAccHolder(customerEntity.get().getFirstName() + customerEntity.get().getLastName());
		accountEntity.setUsername(account.getUsername());
		accountEntity.setBalance(0.0);
		accountEntity.setOpenDate(Calendar.getInstance().getTime().toString());
		accountEntity.setCustomer(customerEntity.get());
		return accountEntity;
	}
	
	
	private Account setDtoParameter( AccountEntity accountEntity) {
		Account account = new Account();
		account.setAccountID(accountEntity.getACCOUNT_ID());
		account.setAccountNumber(MaskBeanService.maskCardNumber(accountEntity.getACCOUNTNUMBER(),4));
		account.setBalance(accountEntity.getBalance());
		account.setCustomerID(accountEntity.getCustomer().getId());
		account.setOpenDate(accountEntity.getOpenDate());
		return account;
		
		
	}
	private String uniqueAccNumberGenerator() {
		Random random = new Random();
		int uniqueNumber = 1000000000 + random.nextInt(90000000);
		return String.valueOf(uniqueNumber);
		
	}
	private Long uniqueNumberGenerator() {
		Random random = new Random();
		long uniqueNumber = 1000000000 + random.nextInt(90000000);
		return uniqueNumber;
		
	}
	
	
	
	
}
