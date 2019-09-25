package com.yudu.face.config;

import com.yudu.face.job.*;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {
    //定时任务1
    @Bean(name = "firstJob")
    public MethodInvokingJobDetailFactoryBean firstJobDetail(FaceKqFirstTask firstTask){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 需要执行的对象
        jobDetail.setTargetObject(firstTask);
        //需要执行的方法
        jobDetail.setTargetMethod("face");
        return jobDetail;
    }

    // 触发器1
    @Bean(name = "firstTrigger")
    public CronTriggerFactoryBean firstTrigger(JobDetail firstJob){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(firstJob);
        //cron表达式，每天6点到23点每1分钟执行一次(从左到右，秒 分 小时 天 月 星期 年) 0 0/30 8-22 * * ? *
        trigger.setCronExpression("0 0/1 6-23 * * ? *");
        return trigger;
    }

    //定时任务2
    @Bean(name = "secondJob")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(FaceKqSecondTask secondTask){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 需要执行的对象
        jobDetail.setTargetObject(secondTask);
        //需要执行的方法
        jobDetail.setTargetMethod("face");
        return jobDetail;
    }

    // 触发器2
    @Bean(name = "secondTrigger")
    public CronTriggerFactoryBean secondTrigger(JobDetail secondJob){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(secondJob);
        trigger.setCronExpression("0 0/1 6-23 * * ? *");
        return trigger;
    }

    //定时任务3
    @Bean(name = "thirdJob")
    public MethodInvokingJobDetailFactoryBean thirdJobDetail(FaceKqThridTask thridTask){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 需要执行的对象
        jobDetail.setTargetObject(thridTask);
        //需要执行的方法
        jobDetail.setTargetMethod("face");
        return jobDetail;
    }

    // 触发器3
    @Bean(name = "thirdTrigger")
    public CronTriggerFactoryBean thirdTrigger(JobDetail thirdJob){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(thirdJob);
        trigger.setCronExpression("0 0/1 6-23 * * ? *");
        return trigger;
    }

    //定时任务4
    @Bean(name = "fourthJob")
    public MethodInvokingJobDetailFactoryBean fourthJobDetail(FaceKqFourthTask fourthTask){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 需要执行的对象
        jobDetail.setTargetObject(fourthTask);
        //需要执行的方法
        jobDetail.setTargetMethod("face");
        return jobDetail;
    }

    // 触发器4
    @Bean(name = "fourthTrigger")
    public CronTriggerFactoryBean fourthTrigger(JobDetail fourthJob){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(fourthJob);
        trigger.setCronExpression("0 0/1 6-23 * * ? *");
        return trigger;
    }

    //定时任务5
    @Bean(name = "fifthJob")
    public MethodInvokingJobDetailFactoryBean fifthJobDetail(FaceKqFifthTask fifthTask){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 需要执行的对象
        jobDetail.setTargetObject(fifthTask);
        //需要执行的方法
        jobDetail.setTargetMethod("face");
        return jobDetail;
    }

    // 触发器5
    @Bean(name = "fifthTrigger")
    public CronTriggerFactoryBean fifthTrigger(JobDetail fifthJob){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(fifthJob);
        trigger.setCronExpression("0 0/1 6-23 * * ? *");
        return trigger;
    }

    // 调度工厂
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger firstTrigger,Trigger secondTrigger,Trigger thirdTrigger,Trigger fourthTrigger,Trigger fifthTrigger) {

        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        //注册触发器
        bean.setTriggers(firstTrigger,secondTrigger,thirdTrigger,fourthTrigger,fifthTrigger);

        return bean;
    }
}
