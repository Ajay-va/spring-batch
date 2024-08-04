package com.batch.processing.config;

import com.batch.processing.entities.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class SpringBatchConfig {




    @Bean
    public FlatFileItemReader<Person> reader(){

        return new FlatFileItemReaderBuilder<Person>()
                .name("customerItemReader")
                .resource(new ClassPathResource("sample_data.csv"))
                .delimited()
                .names("firstName","lastName")
                .targetType(Person.class)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Person>()
                .sql("INSERT INTO people(firstName,lastName) VALUES (:firstName,:lastName)")
                .dataSource(dataSource)
                .beanMapped()
                .build();

    }

    @Bean
    public Job importJobUser(JobRepository jobRepository, Step step1,JobCompletionNotificationListener listener){
        return new JobBuilder("importUserJob",jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<Person> reader,
                      PersonItemProcessor processor,
                      JdbcBatchItemWriter<Person> writer){
        return new StepBuilder("step1",jobRepository)
                .<Person,Person> chunk(3,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/spring_batch?createDatabaseIfNotExist=true")
                .username("root")
                .password("root")
                .build();


    }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

}