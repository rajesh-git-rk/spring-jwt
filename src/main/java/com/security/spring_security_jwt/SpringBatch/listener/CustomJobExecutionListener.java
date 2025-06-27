package com.security.spring_security_jwt.SpringBatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;

public class CustomJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before Job Execution"+jobExecution.getStartTime());
        System.out.println("Start Time: "+jobExecution.getStartTime());
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After Job Execution"+jobExecution.getStartTime());
        System.out.println("End Time: "+jobExecution.getStartTime());
    }


}
