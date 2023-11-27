package com.scotiaBank.ScotiaBank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scotiaBank.ScotiaBank.Entity.AccountEntity;
import com.scotiaBank.ScotiaBank.Entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>{

	List<TransactionEntity> findAllByAccount(AccountEntity account);
	

	
	
	
}
