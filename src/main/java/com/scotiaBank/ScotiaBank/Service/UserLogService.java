package com.scotiaBank.ScotiaBank.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.UserLog;

@Service
public interface UserLogService {

	public void insertUserLog(UserLog userLog);

	List<UserLog> getUserLog(String user);

	public void insertUserLog(String username, long currentTimeMillis, String string);
}
