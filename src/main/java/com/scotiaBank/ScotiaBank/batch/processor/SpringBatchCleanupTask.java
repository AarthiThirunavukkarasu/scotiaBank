package com.scotiaBank.ScotiaBank.batch.processor;

import java.time.LocalDateTime;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBatchCleanupTask implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBatchCleanupTask.class);

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobExplorer explorer;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobLauncher jobLauncher;



    @Override
    public void run(String... args) throws Exception {
    	LOG.info("Starting spring batch cleanup on {}...");

        for (String jobName : jobRegistry.getJobNames()) {

            Set<JobExecution> executions = explorer.findRunningJobExecutions(jobName);

            for (JobExecution jobExecution : executions) {
                // stale job - force cleanup by setting end date and status
                if (isRunningOnThisServer(jobExecution)) {
                    LOG.info("failing job execution id: " + jobExecution.getId());
                    LocalDateTime now = LocalDateTime.now();
                    ExitStatus cleanupTaskExitStatus = new ExitStatus("SERVER_RESTARTED");

                    jobExecution.setStatus(BatchStatus.FAILED);
                    jobExecution.setExitStatus(cleanupTaskExitStatus);
                    jobExecution.setEndTime(now);
                    jobRepository.update(jobExecution);

                    for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
                        if (isRunning(stepExecution)) {
                            stepExecution.setStatus(BatchStatus.FAILED);
                            stepExecution.setExitStatus(cleanupTaskExitStatus);
                            stepExecution.setEndTime(now );
                            jobRepository.update(stepExecution);
                        }
                    }
                }
            }
        }
    }

    private boolean isRunningOnThisServer(JobExecution jobExecution) {
        if (jobExecution.isRunning()) {
            String serverNameParam = jobExecution.getJobParameters()
                    .getString("server_name");
			/*
			 * if (serverNameParam != null && serverNameParam.equals(serverName)) { return
			 * true; }
			 */
        }

        return false;
    }

    private boolean isRunning(StepExecution stepExecution) {
        switch (stepExecution.getStatus()) {
        case STARTED:
        case STARTING:
        case STOPPING:
        case UNKNOWN:
            return true;

        default:
            return stepExecution.getEndTime() == null;
        }
    }
}