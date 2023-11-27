package com.scotiaBank.ScotiaBank.Entity;



import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TRANSACTION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionEntity {

	@Id
    @Column(name = "TRANSACTION_ID")
    private long id;
	
    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;
    
    @Column(name = "AMOUNT")
    private Double amount;
    
    @Column(name= "INFLOWAMOUNT")
    private Double inFlowAmount;
    
    @Column(name= "OUTFLOWAMOUNT")
    private Double outFlowAmount;
    
    
    @Column(name = "TRANSACTION_TIMESTAMP")
    private String transactionTimeStamp;
    
    @Column(name = "USERNAME")
    private String USERNAME;
    
    @ManyToOne
    @JoinColumn(name = "ACCOUNTID")
    private AccountEntity account;

	
	
	
	
}
