package com.yh.business.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String jobMessage = jobExecutionContext.getJobDetail().getJobDataMap().getString("jobMessage");
        String triggerMessage = jobExecutionContext.getTrigger().getJobDataMap().getString("triggerMessage");
        System.out.println("当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        Scheduler scheduler = jobExecutionContext.getScheduler();
        System.out.println("jobMessage：" + jobMessage + "， triggerMessage：" + triggerMessage);
    }
}
