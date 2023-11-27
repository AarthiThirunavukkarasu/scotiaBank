package com.scotiaBank.ScotiaBank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scotiaBank.ScotiaBank.batch.processor.SpringBatchCleanupTask;

@Component
public class BatchJobScheduler {
	@Autowired
	  JobLauncher jobLauncher;
	   
	  @Autowired
	  Job interestCalculationJob;
	  
	  private static final Logger LOG = LoggerFactory.getLogger(BatchJobScheduler.class);
	   
	  @Scheduled(cron = "0 */30 * * * ?") //every 30 mins
	  public void perform() throws Exception 
	  {
		  LOG.info(" Scheduler is running at "+System.currentTimeMillis());
	    JobParameters params = new JobParametersBuilder()
	        .addString("JobID", String.valueOf(System.currentTimeMillis()))
	        .toJobParameters();

	    jobLauncher.run(interestCalculationJob, params);
	  }
}
