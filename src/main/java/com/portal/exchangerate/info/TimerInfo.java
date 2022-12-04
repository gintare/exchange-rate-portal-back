package com.portal.exchangerate.info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimerInfo {
    private int totalFireCount;
    private boolean runForever;
    private long repeatIntervalMs;

    private int repeatIntervalMin;
    private int repeatIntervalHrs;
    private long initialOffsetMs;
    private String callbackData;

}

