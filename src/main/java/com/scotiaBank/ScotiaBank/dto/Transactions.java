package com.scotiaBank.ScotiaBank.dto;

import org.springframework.lang.Nullable;

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
public class Transactions {
	
	@Nullable
	private long id;
	
	private String transactionType;
	
	private Double amount;
	@Nullable
	private String transactionTimeStamp;
	
	private Long accountID;
	@Nullable
    private Double inFlowAmount;
    
	@Nullable
    private Double outFlowAmount;
	
	private String username;
	
	
	
	
	
	
	
	
	

}
