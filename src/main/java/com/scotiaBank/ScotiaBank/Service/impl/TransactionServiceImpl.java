package com.scotiaBank.ScotiaBank.Service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.AccountEntity;
import com.scotiaBank.ScotiaBank.Entity.TransactionEntity;
import com.scotiaBank.ScotiaBank.Exception.AccountNotFoundException;
import com.scotiaBank.ScotiaBank.Exception.TransactionFailedException;
import com.scotiaBank.ScotiaBank.Service.EmailService;
import com.scotiaBank.ScotiaBank.Service.TransactionService;
import com.scotiaBank.ScotiaBank.dto.Transactions;
import com.scotiaBank.ScotiaBank.repositories.AccountRepository;
import com.scotiaBank.ScotiaBank.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl  implements TransactionService{
	 private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	EmailService emailService;

	@Override
	public List<TransactionEntity> addALL(List<TransactionEntity> transEn) {
		LOG.info(" You are in Service Impl");
		return transactionRepository.saveAll(transEn);
	}

	@Override
	public Transactions performTransaction(Transactions transaction, String username) throws TransactionFailedException,AccountNotFoundException{
		AccountEntity accountdetails = new AccountEntity();
		TransactionEntity transactionEntity = new TransactionEntity();
		TransactionEntity transactionResult = new TransactionEntity();
		Transactions result = new Transactions();
		try {
			//accountdetails = accountRepository.getById(transaction.getAccountID());
			accountdetails = accountRepository.findById(transaction.getAccountID())
	                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + transaction.getAccountID()));
			if(!checkBalance(accountdetails,transaction)) {
				throw new TransactionFailedException ("TransactionFailed: Insufficient balance");
			}
			transactionEntity.setUSERNAME(transaction.getUsername());
			//Deposit
			if(transaction.getTransactionType().equalsIgnoreCase("deposit")) {
				transactionEntity.setId(generateUniqueId());
				transactionEntity.setTransactionTimeStamp(Calendar.getInstance().getTime().toString());
				transactionEntity.setInFlowAmount(transaction.getAmount());
				transactionEntity.setOutFlowAmount(0.0);
				transactionEntity.setAmount(accountdetails.getBalance() + transaction.getAmount());
				transactionEntity.setTransactionType(transaction.getTransactionType());
				transactionEntity.setAccount(accountdetails);			

			}
			//Withdraw
			if(transaction.getTransactionType().equalsIgnoreCase("withdraw")){
				transactionEntity.setId(generateUniqueId());
				transactionEntity.setTransactionTimeStamp(Calendar.getInstance().getTime().toString());
				transactionEntity.setInFlowAmount(0.0);
				transactionEntity.setOutFlowAmount(transaction.getAmount());
				transactionEntity.setAmount(accountdetails.getBalance() - transaction.getAmount());
				transactionEntity.setTransactionType(transaction.getTransactionType());
				transactionEntity.setAccount(accountdetails);

			}
			
			//Transfer
			if(transaction.getTransactionType().equalsIgnoreCase("transfer")) {
				transactionEntity.setId(generateUniqueId());
				transactionEntity.setTransactionTimeStamp(Calendar.getInstance().getTime().toString());
				transactionEntity.setInFlowAmount(0.0);
				transactionEntity.setOutFlowAmount(transaction.getAmount());
				transactionEntity.setAmount(accountdetails.getBalance() - transaction.getAmount());
				transactionEntity.setTransactionType(transaction.getTransactionType());
				transactionEntity.setAccount(accountdetails);
				

			}
			transactionResult = transactionRepository.save(transactionEntity);
			//balance reflect in Account
			accountdetails.setBalance(transactionResult.getAmount());
			accountdetails = accountRepository.save(accountdetails);
			LOG.info("TransactionServiceImpl"+accountdetails.toString());
			result = setDtoParameter(transactionResult);
			emailService.sendEmail(accountdetails.getCustomer().getEmailId(), "Transcation Alert", getBody(accountdetails,result));
			
			
		}catch(AccountNotFoundException ce) {
			LOG.error("TransactionServiceImpl"+ce);
			 throw new AccountNotFoundException(ce.getMessage());
		}catch(TransactionFailedException te) {
			LOG.error("TransactionServiceImpl"+te);
			throw new TransactionFailedException ("TransactionFailed: Insufficient balance");
		}
		
		catch(Exception e) {
			LOG.error("TransactionServiceImpl"+e);
		}finally {
			
		}
		return result;
	}

	

    private String getBody(AccountEntity accountdetails, Transactions result) {
    	 String htmlBody = "<html>"
    	 		+ "<body><h1>Hi </h1>" +accountdetails.getCustomer().getFirstName()
    	 		+ "<p>Transcation details amount</p>"+result.getTransactionType()+ " Amount:"+result.getInFlowAmount()+ "balance"+result.getAmount()
    	 		+ "</body></html>";

		return htmlBody;
	}

	private boolean checkBalance(AccountEntity accountdetails, Transactions transaction) {
    	boolean transAllowed = false;
		if((transaction.getTransactionType().equalsIgnoreCase("withdraw"))||(transaction.getTransactionType().equalsIgnoreCase("transfer"))) {
			if((accountdetails.getBalance()>=1)&& (transaction.getAmount()<=accountdetails.getBalance())){
				
				transAllowed = true;
				
			}else {
				transAllowed = false;
			}
			
		}else {
			transAllowed = true;
		}
		return transAllowed;
	}

	private  long generateUniqueId() {
    	int DIGIT_COUNT = 8;
        long MAX_VALUE = (long) Math.pow(10, DIGIT_COUNT);
        Set<Long> usedIds = new HashSet<>();
        Random random = new Random();
        long uniqueId;

        do {
            uniqueId = random.nextLong() % MAX_VALUE;
            if (uniqueId < 0) {
                uniqueId = -uniqueId; // Ensure it's positive
            }
        } while (usedIds.contains(uniqueId));

        usedIds.add(uniqueId);

        return uniqueId;
    }

	@Override
	public List<Transactions> getStatements(long accountId) {
		List<TransactionEntity> transactionEntity = new ArrayList<TransactionEntity>();
		List<Transactions> statements = new ArrayList<Transactions>();
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            AccountEntity account = optionalAccount.get();
            transactionEntity = transactionRepository.findAllByAccount(account);
        } else {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
        for(TransactionEntity trans : transactionEntity) {
        	Transactions statement = new Transactions();
        	statement.setId(trans.getId());
        	statement.setTransactionType(trans.getTransactionType());
        	statement.setTransactionTimeStamp(trans.getTransactionTimeStamp());
        	statement.setInFlowAmount(trans.getInFlowAmount());
        	statement.setOutFlowAmount(trans.getOutFlowAmount());
        	statement.setAmount(trans.getAmount());
        	statement.setAccountID(trans.getAccount().getACCOUNT_ID());
        	statements.add(statement);
        }
        
        return statements;
        
	}
	private Transactions setDtoParameter( TransactionEntity transactionEntity) {
		Transactions transactions = new Transactions();
		transactions.setAccountID(transactionEntity.getAccount().getACCOUNT_ID());
		transactions.setAmount(transactionEntity.getAmount());
		transactions.setId(transactionEntity.getId());
		transactions.setInFlowAmount(transactionEntity.getInFlowAmount());
		transactions.setOutFlowAmount(transactionEntity.getOutFlowAmount());
		transactions.setTransactionTimeStamp(transactionEntity.getTransactionTimeStamp());
		transactions.setTransactionType(transactionEntity.getTransactionType());
		transactions.setUsername(transactionEntity.getUSERNAME());
		return transactions;
	}	
		
	
}
