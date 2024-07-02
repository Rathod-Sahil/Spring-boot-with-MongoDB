package com.first.service;

import org.joda.time.DateTime;
import org.springframework.scheduling.quartz.QuartzJobBean;


public interface SchedulingService {

    void cronSchedule(String groupName, Class<? extends QuartzJobBean> jobClass, String cronExpression);
    void dateSchedule(String groupName, Class<? extends QuartzJobBean> jobClass, DateTime startDate);
}
