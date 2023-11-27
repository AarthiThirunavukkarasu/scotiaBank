package com.scotiaBank.ScotiaBank.batch.processor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scotiaBank.ScotiaBank.Entity.AccountEntity;

@Configuration
@EnableBatchProcessing
public class InterestCalculationProcessorTestConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<AccountEntity> reader;

    @Autowired
    private ItemProcessor<AccountEntity, AccountEntity> processor;

    @Autowired
    private ItemWriter<AccountEntity> writer;

    @Bean
    public Job interestCalculationJob() {
        return jobBuilderFactory.get("interestCalculationJob")
                .start(interestCalculationStep())
                .build();
    }

    @Bean
    public Step interestCalculationStep() {
        return stepBuilderFactory.get("interestCalculationStep")
                .<AccountEntity, AccountEntity>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<AccountEntity> reader() {
		return reader;
       
    }

    @Bean
    public ItemProcessor<AccountEntity, AccountEntity> processor() {
        return new InterestCalculationProcessor();
    }

    @Bean
    public ItemWriter<AccountEntity> writer() {
        return writer;
    }
}
