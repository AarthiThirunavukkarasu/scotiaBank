package com.scotiaBank.ScotiaBank.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scotiaBank.ScotiaBank.Entity.AccountHistory;
import com.scotiaBank.ScotiaBank.Entity.UserLog;
import com.scotiaBank.ScotiaBank.Service.AccountHistoryService;
import com.scotiaBank.ScotiaBank.repositories.AccountHistoryRepoistory;

@Component
public class AccountHistoryServiceImpl implements AccountHistoryService{

	@Autowired
	AccountHistoryRepoistory accountHistoryRepoistory;
	
	
	@Override
	public void insertAccountLog(AccountHistory accountHistory) {
		accountHistoryRepoistory.save(accountHistory);
		
	}
	
	@Override
	public List<AccountHistory> getAccountLog(long accountid) {
		return accountHistoryRepoistory.findAllByAccountID(accountid);
		
	}

	@Override
	public void insertAccountLog(long accountId, long currentTimeMillis, String action) {
		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setAccountID(accountId);
		accountHistory.setTimeStamp(currentTimeMillis);
		accountHistory.setActions(action);
		accountHistoryRepoistory.save(accountHistory);
	}

}
