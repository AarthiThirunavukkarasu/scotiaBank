package com.scotiaBank.ScotiaBank.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scotiaBank.ScotiaBank.Entity.UserLog;
import com.scotiaBank.ScotiaBank.Service.UserLogService;
import com.scotiaBank.ScotiaBank.repositories.UserLogRepoistory;

@Component
public class UserLogServiceImpl implements UserLogService{

	@Autowired
	UserLogRepoistory userLogRepoistory;
	
	
	@Override
	public void insertUserLog(UserLog userLog) {
		userLogRepoistory.save(userLog);
		
	}
	
	@Override
	public List<UserLog> getUserLog(String user) {
		return userLogRepoistory.findAllByUsername(user);
		
	}

	@Override
	public void insertUserLog(String username, long currentTimeMillis, String action) {
		UserLog userLog = new UserLog();
		userLog.setActions(action);
		userLog.setTimeStamp(currentTimeMillis);
		userLog.setUsername(username);
		userLogRepoistory.save(userLog);
		
	}

}
