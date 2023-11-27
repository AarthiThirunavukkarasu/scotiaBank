package com.scotiaBank.ScotiaBank.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.AccountHistory;
import com.scotiaBank.ScotiaBank.Entity.UserLog;

@Service
public interface AccountHistoryService {



	void insertAccountLog(AccountHistory accountHistory);

	List<AccountHistory> getAccountLog(long accountID);

	void insertAccountLog(long accountId, long currentTimeMillis, String string);
}
