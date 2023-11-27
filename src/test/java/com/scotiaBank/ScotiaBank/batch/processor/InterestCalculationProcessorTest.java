package com.scotiaBank.ScotiaBank.batch.processor;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringJUnitConfig(InterestCalculationProcessorTestConfig.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class InterestCalculationProcessorTest {

    @Autowired
    private JobLauncher jobLauncherTestUtils;

    @Autowired
    private JobRepository jobRepositoryTestUtils;

    @Test
    public void testJob() throws Exception {
       
       // ((Object) jobLauncherTestUtils).launchJob();

        
    }
}
