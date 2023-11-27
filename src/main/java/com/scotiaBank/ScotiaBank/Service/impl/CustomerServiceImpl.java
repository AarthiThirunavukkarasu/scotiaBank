package com.scotiaBank.ScotiaBank.Service.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.AccountEntity;
import com.scotiaBank.ScotiaBank.Entity.CustomerEntity;
import com.scotiaBank.ScotiaBank.Exception.CustomerAlreadyExistsException;
import com.scotiaBank.ScotiaBank.Service.CustomerService;
import com.scotiaBank.ScotiaBank.Service.DecryptBean;
import com.scotiaBank.ScotiaBank.Service.EncryptBean;
import com.scotiaBank.ScotiaBank.Service.MaskBeanService;
import com.scotiaBank.ScotiaBank.dto.Customer;
import com.scotiaBank.ScotiaBank.repositories.AccountRepository;
import com.scotiaBank.ScotiaBank.repositories.CustomerRepository;
import com.scotiaBank.ScotiaBank.repositories.TransactionRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());
	 private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public List<CustomerEntity> addALL(List<CustomerEntity> cus) {
		LOG.info("CustomerServiceImpl ::addALL Service Impl");
		return customerRepository.saveAll(cus);
	}

	@Override
	public Customer createCustomer(Customer customer) throws CustomerAlreadyExistsException {
		LOG.info("CustomerServiceImpl :: createCustomer Service Impl");
		Customer newCustomer = new Customer();
		CustomerEntity addcustomer = null;
		try {
             System.out.println("customer"+customerRepository.existsBySinIgnoreCase(customer.getSin()));
            
	        if (customerRepository.existsBySinIgnoreCase(customer.getSin())) {
	        	LOG.info("CustomerServiceImpl :: createCustomer  sin exists");
	            throw new CustomerAlreadyExistsException("Customer with SIN " + customer.getSin() + " already exists.");
	        }
			
	        addcustomer =  setEntityParameter(customer);
	        addcustomer.setId(uniqueNumberGenerator());
	        CustomerEntity resultCus = customerRepository.save(addcustomer);
	        newCustomer = setDtoParameter(resultCus);

	        LOG.info("CustomerServiceImpl :: saved customer ");

		}catch(CustomerAlreadyExistsException ce) {
			 throw new CustomerAlreadyExistsException(ce.getMessage());
		}
		catch (Exception e) {
			LOG.error("CustomerServiceImpl ::createcustomer"+e);
			// e.printStackTrace();
		} finally {

		}
		return newCustomer;
	}

	private CustomerEntity setEntityParameter( Customer customer) throws Exception {
		
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setEmailId(customer.getEmailId());
		customerEntity.setPhoneNumber(customer.getPhoneNumber());
		customerEntity.setPostalCode(customer.getPostalCode());
		customerEntity.setSin(EncryptBean.encrypt(customer.getSin()));
		customerEntity.setAddress(customer.getAddress());
		
		return customerEntity;
		
	}
	
	private Customer setDtoParameter( CustomerEntity customerEntity) throws Exception {
		Customer customer = new Customer();
		customer.setCustomerID(customerEntity.getId());
		customer.setFirstName(customerEntity.getFirstName());
		customer.setLastName(customerEntity.getLastName());
		customer.setEmailId(MaskBeanService.maskCardNumber(customerEntity.getEmailId(),4));
		customer.setPhoneNumber(MaskBeanService.maskCardNumber(customerEntity.getPhoneNumber(),4));
		customer.setPostalCode(customerEntity.getPostalCode());
		customer.setAddress(customerEntity.getAddress());
		customer.setSin(MaskBeanService.maskCardNumber(DecryptBean.decrypt(customerEntity.getSin()),4));
		return customer;
		
		
	}

	@Override
	public CustomerEntity getCustomer() {
		Long Id = (long) 1;
		// TODO Auto-generated method stub
		return customerRepository.getById(Id);
	}
	
	private Long uniqueNumberGenerator() {
		Random random = new Random();
		long uniqueNumber = 100000 + random.nextInt(900000);
		return uniqueNumber;
		
	}

}
/*
 * System.out.println(resultCus.getId()); //default account for new user
 * AccountEntity defaultAccount = new AccountEntity();
 * defaultAccount.setAccNum(generateAccountNumber());
 * defaultAccount.setAccHolder(cus.getFirstName() + cus.getLastName());
 * defaultAccount.setOpenDate(Calendar.getInstance().getTime().toString());
 * defaultAccount.setBalance(0.0); defaultAccount.setCustomer(cus);
 * AccountEntity savedAccount = accountRepository.save(defaultAccount);
 * System.out.println(" saved Account "); Create a default transaction for the
 * default account TransactionEntity defaultTransaction = new
 * TransactionEntity();
 * defaultTransaction.setTransactionType("Initial Deposit");
 * defaultTransaction.setAmount(0.0);
 * defaultTransaction.setTransactionTimeStamp(Calendar.getInstance().getTime());
 * defaultTransaction.setAccount(savedAccount);
 * transactionRepository.save(defaultTransaction); List<TransactionEntity>
 * transList = new ArrayList<TransactionEntity>();
 * transList.add(defaultTransaction); savedAccount.setTransactions(transList);
 * List<AccountEntity> accounts = new ArrayList<>(); accounts.add(savedAccount);
 cus.setAccounts(accounts);

resultCus = customerRepository.save(cus);*/ 