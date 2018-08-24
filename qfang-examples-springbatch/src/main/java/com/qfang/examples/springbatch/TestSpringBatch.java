package com.qfang.examples.springbatch;

import com.qfang.examples.springbatch.aggregator.PersonLineAggregator;
import com.qfang.examples.springbatch.dto.PersonDto;
import com.qfang.examples.springbatch.mapper.PersonLineMapper;
import com.qfang.examples.springbatch.processor.PersonItemProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSpringBatch {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private  PlatformTransactionManager transactionManager;

    @Resource(name="bakReportJob")
    private Job bakReportJob;

    @Test
    public void test1() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        FlatFileItemReader<PersonDto> flatFileItemReader=new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/batch-data.csv"));
        flatFileItemReader.setLineMapper(new PersonLineMapper());

        PersonItemProcessor personItemProcessor=new PersonItemProcessor();
        FlatFileItemWriter<PersonDto> flatFileItemWriter=new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(new FileSystemResource("src/main/resources/batch-data2.csv"));
        flatFileItemWriter.setLineAggregator(new PersonLineAggregator());

        StepBuilderFactory stepBuilderFactory=new StepBuilderFactory(jobRepository,transactionManager);
        Step step= stepBuilderFactory.get("step")
                .<PersonDto,PersonDto>chunk(1)
                .reader(flatFileItemReader)
                .processor(personItemProcessor)
                .writer(flatFileItemWriter)
                .build();

        JobBuilderFactory jobBuilderFactory= new JobBuilderFactory(jobRepository);
        Job job=jobBuilderFactory.get("job").start(step).build();
        jobLauncher.run(job,new JobParameters());
    }

    @Test
    public void test2() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(bakReportJob,new JobParameters());
    }






}
