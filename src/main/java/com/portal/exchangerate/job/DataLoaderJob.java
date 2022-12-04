package com.portal.exchangerate.job;

import com.portal.exchangerate.info.Constants;
import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.repository.CurrencyRepository;
import com.portal.exchangerate.service.CurrencyLoader;
import com.portal.exchangerate.service.ExchangeRateCurrentLoader;
import com.portal.exchangerate.service.ExchangeRateHistoryLoader;
import com.portal.exchangerate.utils.TimerUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataLoaderJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(DataLoaderJob.class);
    @Autowired
    private final CurrencyRepository currencyRepository = null;
    private final ExchangeRateHistoryLoader exchangeRateHistoryLoader;
    private final CurrencyLoader currencyLoader;
    private final ExchangeRateCurrentLoader exchangeRateCurrentLoader;

    @Autowired
    public DataLoaderJob(ExchangeRateHistoryLoader exchangeRateHistoryLoader, CurrencyLoader currencyLoader, ExchangeRateCurrentLoader exchangeRateCurrentLoader) {
        this.exchangeRateHistoryLoader = exchangeRateHistoryLoader;
        this.currencyLoader = currencyLoader;
        this.exchangeRateCurrentLoader = exchangeRateCurrentLoader;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        currencyLoader.reset();
        StringBuilder result = currencyLoader.ladXmlDataFromInternet();
        currencyLoader.loadXmlDataToDB(result);

        this.exchangeRateCurrentLoader.reset();
        StringBuilder result1 = this.exchangeRateCurrentLoader.ladXmlDataFromInternet();
        this.exchangeRateCurrentLoader.loadXmlDataToDB(result1);

        this.exchangeRateHistoryLoader.reset();

        this.exchangeRateHistoryLoader.setCurrecyName(Constants.STARTER_CURRENCY_NAME);
        this.exchangeRateHistoryLoader.setDateFrom(TimerUtils.now());
        this.exchangeRateHistoryLoader.setDateTo(TimerUtils.threeMonthsAgo());
        StringBuilder result2 = this.exchangeRateHistoryLoader.ladXmlDataFromInternet();
        this.exchangeRateHistoryLoader.loadXmlDataToDB(result2);

        log.info("now = "+TimerUtils.now()+", 3 months ago = "+TimerUtils.threeMonthsAgo());

    }
}
