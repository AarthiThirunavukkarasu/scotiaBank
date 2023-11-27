package com.scotiaBank.ScotiaBank.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ACCOUNT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

	


	@Id
	@Column(name = "ACCOUNT_ID", nullable = false)
    private long ACCOUNT_ID;
	
    @Column(name = "ACCOUNTNUMBER", nullable = false)
    private String ACCOUNTNUMBER;
    
    @Column(name = "ACCOUNT_HOLDER")
    private String accHolder;
    
    @Column(name = "BALANCE")
    private double balance;    
    
    @Column(name = "OPENED_DATE", nullable = false)
    private String openDate;
    
    @Column(name = "USERNAME")
    private String username;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMERID", referencedColumnName = "CUSTOMER_ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CustomerEntity customer;
    
   

}
