package com.scotiaBank.ScotiaBank.dto;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scotiaBank.ScotiaBank.Entity.CustomerEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
	
	@Nullable
	private Long accountID;
	@Nullable
	private String accountNumber;
	@Nullable
	private double balance;
	@Nullable
	private String openDate;
	
	private long customerID;
	
	private String username;
	
	
	
	
	
	
	
	

}
