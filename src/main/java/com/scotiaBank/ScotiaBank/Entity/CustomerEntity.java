package com.scotiaBank.ScotiaBank.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Entity
@Table(name = "CUSTOMER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
	


		@Id
		@Column(name = "CUSTOMER_ID",unique = true)
	    private Long id;
	    
	    @Column(name = "FIRSTNAME", nullable = false)
	    private String firstName;
	    
	    @Column(name = "LASTNAME", nullable = false)
	    private String lastName;
	    
	    @Column(name = "EMAIL", nullable = false, unique = true)
	    private String emailId;

	    @Column(name = "SIN", nullable = false, unique = true)
	    private String sin;
	    
	    @Column(name = "ADDRESS", nullable = false)
	    private String address;
	    
	    @Column(name = "POSTALCODE", nullable = false)
	    private String postalCode;
	    
	    @Column(name = "PHONEMUMBER", nullable = false, unique = true)
	    private String phoneNumber;
	    
	    
	    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	    private List<AccountEntity> accounts;

		
	    
	    
		
	    
	    
	    

}
