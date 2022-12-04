package com.portal.exchangerate.service;

import com.portal.exchangerate.info.TimerInfo;
import com.portal.exchangerate.job.DataLoaderJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataLoaderService {
    private final SchedulerService scheduler;

    @Autowired
    public DataLoaderService(final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runDataLoaderJob(){
        final TimerInfo info = new TimerInfo();
        info.setRunForever(true);
        info.setRepeatIntervalHrs(24);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My Callback data");

        scheduler.schedule(DataLoaderJob.class, info    );
    }
}
