package com.scotiaBank.ScotiaBank.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scotiaBank.ScotiaBank.Entity.UserLog;
import com.scotiaBank.ScotiaBank.Service.AccountHistoryService;
import com.scotiaBank.ScotiaBank.Service.UserLogService;
@RestController("/api/")
public class FootPrintsController {
	@Autowired
	UserLogService userLogService;
	
	
	@Autowired
	AccountHistoryService accountHistoryService;
	
	@GetMapping("/userHistory")
	public List<UserLog> searchUserHistory(@RequestParam String username){
		return userLogService.getUserLog(username);
	}
	

	
	@GetMapping("/accountHistory")
	public List<UserLog> searchAccountHistory(@RequestParam long accountID){
		return accountHistoryService.getAccountLog(accountID);
	}
}
