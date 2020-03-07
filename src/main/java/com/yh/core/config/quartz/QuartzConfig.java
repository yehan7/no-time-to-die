package com.yh.core.config.quartz;

import com.yh.business.schedule.TestTask;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@ConfigurationProperties(prefix = "quartz")
public class QuartzConfig {


    @Autowired
    private JobFactory jobFactory;

    private String enable;


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
       /* // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //延长启动
        schedulerFactoryBean.setStartupDelay(1);
        //设置加载的配置文件
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));*/
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        while(enable.equals("false")){
            return null;
        }
        Scheduler scheduler = schedulerFactoryBean().getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(TestTask.class).
                withIdentity("yh").usingJobData("jobMessage", "test").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("yh").
                usingJobData("triggerMessage", "testjob").
                withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?")).build();

        scheduler.scheduleJob(jobDetail, trigger);

        return scheduler;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
