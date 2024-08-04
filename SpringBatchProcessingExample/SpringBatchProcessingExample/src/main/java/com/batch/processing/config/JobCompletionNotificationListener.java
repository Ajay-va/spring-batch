package com.batch.processing.config;

import com.batch.processing.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger LOGGER= LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus()== BatchStatus.COMPLETED){

            LOGGER.info("!!!! JOB FINISHED ! Time to verify the result...!!!");

            jdbcTemplate.query("SELECT firstName,lastName FROM people "
                    ,new DataClassRowMapper<>(Person.class))
                    .forEach(person -> LOGGER.info("Found<{{}}> in the database .",person));


        }
    }
}