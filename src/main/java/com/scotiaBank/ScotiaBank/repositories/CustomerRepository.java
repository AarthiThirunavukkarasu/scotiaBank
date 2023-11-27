package com.scotiaBank.ScotiaBank.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.scotiaBank.ScotiaBank.Entity.CustomerEntity;

@Repository
@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>{

		boolean existsBySin(String sin);

		boolean existsById(int customerID);

		Optional<CustomerEntity> findById(int customerID);

		boolean findBySin(String sin);

		boolean existsBySinIgnoreCase(String sin);
	

	
	
	
}
