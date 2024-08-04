package com.batch.processing.controller;

import com.batch.processing.config.PersonItemProcessor;
import com.batch.processing.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {


    private static final Logger LOGGER= LoggerFactory.getLogger(BatchController.class);

    @Autowired
    private PersonItemProcessor itemProcessor;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/send")
    public String batchCalling() throws Exception {


        JobParameters jobParameters=new JobParametersBuilder()
                .addLong("startAt",System.currentTimeMillis())
                        .toJobParameters();

        try {
            jobLauncher.run(job,jobParameters);
        }catch (JobExecutionException jee){
            System.out.println(jee);
        }
        LOGGER.info("person details sharing here...!!!!");
        return "SEND DATA PERSON DATA HERE...!!!";

    }
}
