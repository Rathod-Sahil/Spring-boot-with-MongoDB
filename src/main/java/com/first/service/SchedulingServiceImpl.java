package com.first.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulingServiceImpl implements SchedulingService{
    private final Scheduler scheduler;

    private JobDetail jobDetail(String groupName, Class<? extends QuartzJobBean> jobClass) {
        return JobBuilder.newJob(jobClass).withIdentity(groupName).build();
    }

    private void scheduleJob(JobDetail jobDetail, Trigger trigger, String exception) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(exception, e);
        }
    }
    public void cronSchedule(String groupName, Class<? extends QuartzJobBean> jobClass, String cronExpression) {
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        scheduleJob(jobDetail(groupName, jobClass), trigger, "Cron scheduler exception");
    }

    public void dateSchedule(String groupName, Class<? extends QuartzJobBean> jobClass, DateTime startDate) {
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(groupName)
                .startAt(startDate.toDate())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
        scheduleJob(jobDetail(groupName, jobClass), trigger, "Date scheduler exception");
    }
}
