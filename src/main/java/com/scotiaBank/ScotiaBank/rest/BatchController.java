package com.scotiaBank.ScotiaBank.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job interestCalculationJob;
	
	private static final Logger LOG = LoggerFactory.getLogger(BatchController.class);
	@GetMapping
	
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		Map<String, JobParameter> parameters = new HashMap<>();
		//parameters.put("time", new JobParameter(System.currentTimeMillis(), null));
		JobParameters jobParameter = new JobParameters();
		JobExecution jobExecution = jobLauncher.run(interestCalculationJob, new JobParameters());
		 
		LOG.info(" Status"+jobExecution.getStatus());
		return jobExecution.getStatus();
		
	}
	
	

}
