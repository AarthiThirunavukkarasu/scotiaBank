package com.scotiaBank.ScotiaBank.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERLOG")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLog {

	@Id
	@Column(name = "TIMESTAMP")
	private long timeStamp;
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "ACTIONS")
	private String actions;
	
	
	
	
}
