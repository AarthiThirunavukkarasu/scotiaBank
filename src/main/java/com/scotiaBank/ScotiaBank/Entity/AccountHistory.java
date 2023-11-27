package com.scotiaBank.ScotiaBank.Entity;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AccountHistory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory {

	@Id
	@Column(name = "timeStamp")
	private long timeStamp;
	@Column(name = "accountID")
	private long accountID;

	@Column(name = "actions")
	private String actions;
	
	
	
	
}
