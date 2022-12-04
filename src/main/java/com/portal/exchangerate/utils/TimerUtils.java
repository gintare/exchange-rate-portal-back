package com.portal.exchangerate.utils;


import com.portal.exchangerate.info.Constants;
import com.portal.exchangerate.info.TimerInfo;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimerUtils {
    public TimerUtils() {
    }

    public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info){
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), info);

        return JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap).build();
    }

    public static Trigger buildTrigger(final Class jobClass, final TimerInfo info){
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(info.getRepeatIntervalHrs());

        if(info.isRunForever()){
            builder = builder.repeatForever();
        }else{
            builder = builder.withRepeatCount(info.getTotalFireCount()-1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis()+info.getInitialOffsetMs()))
                .build();
    }

    public static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        return sdf.format(new Date());
    }
    public static String threeMonthsAgo() {
        Date date = java.sql.Date.valueOf(LocalDate.now().minus(Constants.MONTHS_FROM_NOW, ChronoUnit.MONTHS));
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        return sdf.format(date);
    }


}

