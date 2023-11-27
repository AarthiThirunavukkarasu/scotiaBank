package com.scotiaBank.ScotiaBank.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.support.DefaultDataFieldMaxValueIncrementerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.scotiaBank.ScotiaBank.Entity.AccountEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Configuration
@EnableBatchProcessing
public class InterestCalculationJobConfig {
	 private static final Logger LOG = LoggerFactory.getLogger(InterestCalculationJobConfig.class);
	@Autowired
	private DataSource dataSource;


	@Autowired
	 EntityManagerFactory entityManagerFactory;
	@PersistenceContext
	private EntityManager entityManager;


	@Bean(name = "savingsAccountReader")
	@Transactional
	public ItemReader<AccountEntity> savingsAccountReader() {
		LOG.info(" inside Reader");
		JpaPagingItemReader<AccountEntity> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("SELECT a FROM AccountEntity a WHERE a.balance > 0");
		return reader;
	}

	@Bean(name = "savingsAccountWriter")
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public ItemWriter<AccountEntity> savingsAccountWriter() {
		LOG.info(" inside writer");
		 JpaItemWriter<AccountEntity> writer = new JpaItemWriter<>();
	     writer.setEntityManagerFactory(entityManagerFactory);
	     //entityManager.flush();
		return writer;
	}

	@Bean(name = "jobLauncher")
	public JobLauncher jobLauncher(JpaTransactionManager transactionManager) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(createJobRepository(transactionManager));
		return jobLauncher;
	}

	@Bean
	public JobRepository createJobRepository(PlatformTransactionManager transactionManager) throws Exception {
		JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
		factoryBean.setDatabaseType("ORACLE");
		factoryBean.setDataSource(dataSource);
		factoryBean.setTransactionManager(transactionManager);
		factoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		factoryBean.setIncrementerFactory(new DefaultDataFieldMaxValueIncrementerFactory(dataSource));
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}
	@Bean(name="interestCalculationStep")
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Step interestCalculationStep(ItemReader<AccountEntity> savingsAccountReader, JobRepository jobRepository,
	                                    ItemProcessor<AccountEntity, AccountEntity> processor,
	                                    ItemWriter<AccountEntity> savingsAccountWriter, JpaTransactionManager transactionManager) {
	    return new StepBuilder("interestCalculationStep", jobRepository)
	    		.<AccountEntity, AccountEntity> chunk(3, transactionManager)
	    	    .reader(savingsAccountReader)
	    	    .processor(processor)
	    	    .writer(savingsAccountWriter)
	    	    .allowStartIfComplete(true)
	    	    .build();
	}
	@Bean
	public Job interestCalculationJob(Step interestCalculationStep, JobRepository jobRepository, JpaTransactionManager transactionManager) {
	    return new JobBuilder("interestCalculationJob", jobRepository)
	    		.incrementer(new RunIdIncrementer())
	    	    .start(interestCalculationStep)
	    	    .build();
	}


	

	
	/*
	 * @Bean public JobRepository jobRepository() throws Exception{
	 * JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
	 * factoryBean.setDatabaseType("ORACLE"); factoryBean.setDataSource(dataSource);
	 * factoryBean.setTransactionManager(transactionManager());
	 * factoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
	 * factoryBean.setIncrementerFactory(new
	 * DefaultDataFieldMaxValueIncrementerFactory(dataSource));// {
	 * 
	 * @Override public DataFieldMaxValueIncrementer getIncrementer(String
	 * incrementerType, String incrementerName) { return new
	 * DataFieldMaxValueIncrementer(dataSource, incrementerName); } });
	 * 
	 * factoryBean.afterPropertiesSet(); return factoryBean.getObject(); }
	 */

}