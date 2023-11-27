package com.scotiaBank.ScotiaBank.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.scotiaBank.ScotiaBank.Entity.AccountEntity;
import com.scotiaBank.ScotiaBank.Service.AuditLog;

import jakarta.transaction.Transactional;

@Component
public class InterestCalculationProcessor implements ItemProcessor<AccountEntity, AccountEntity> {

	private static final Logger LOG = LoggerFactory.getLogger(InterestCalculationProcessor.class);
    @Override
    @Transactional
    public AccountEntity process(AccountEntity account) {
    	LOG.info("inside processor");
        double interestRate = 0.05; //  interest rate (5%)
        double interest = account.getBalance() * interestRate;
        account.setBalance(account.getBalance() + interest);
        LOG.info("Account ID: " + account.getACCOUNT_ID() + ", New Balance: " + account.getBalance());
        AuditLog.auditLog("BatchJob", "updating interest", System.currentTimeMillis(), "Updating Balance "+account.getBalance(), ""+account.getACCOUNT_ID());
        return account;
    }

}

