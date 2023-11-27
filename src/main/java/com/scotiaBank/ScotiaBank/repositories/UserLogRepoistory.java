package com.scotiaBank.ScotiaBank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scotiaBank.ScotiaBank.Entity.UserLog;

@Repository
public interface UserLogRepoistory extends JpaRepository<UserLog, Long>{

	

	List<UserLog> findAllByUsername(String user);

}
