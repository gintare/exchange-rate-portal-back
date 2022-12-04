package com.portal.exchangerate.controller;

import com.portal.exchangerate.info.Constants;
import com.portal.exchangerate.job.DataLoaderJob;
import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.model.ExchangeRateHistory;
import com.portal.exchangerate.repository.CurrencyRepository;
import com.portal.exchangerate.repository.ExchangeRateHistoryRepository;
import com.portal.exchangerate.service.ExchangeRateHistoryLoader;
import com.portal.exchangerate.utils.TimerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v2/exchange_rate_history")
public class ExchangeRateHistoryController {
    private static final Logger log = LoggerFactory.getLogger(ExchangeRateHistoryController.class);
    @Autowired
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository = null;
    @Autowired
    private final CurrencyRepository currencyRepository = null;
    private final ExchangeRateHistoryLoader exchangeRateHistoryLoader;

    @Autowired
    public ExchangeRateHistoryController(ExchangeRateHistoryLoader exchangeRateHistoryLoader) {
        this.exchangeRateHistoryLoader = exchangeRateHistoryLoader;
    }

    @GetMapping
    public List<ExchangeRateHistory> findAll(){ return this.exchangeRateHistoryRepository.findAll(); }

    @GetMapping(value = "{currencyId}")
    public List<ExchangeRateHistory> findByCurrencyId(@PathVariable final int currencyId){
        List<ExchangeRateHistory> list = this.exchangeRateHistoryRepository.findByCurrencyId(currencyId);
        if(list.size() == 0){
            String currencyName = this.currencyRepository.findById(currencyId).orElse(new Currency()).getName();
            log.info("currencyName = "+currencyName);
            this.exchangeRateHistoryLoader.setCurrecyName(currencyName);
            this.exchangeRateHistoryLoader.setDateFrom(TimerUtils.now());
            this.exchangeRateHistoryLoader.setDateTo(TimerUtils.threeMonthsAgo());
            StringBuilder result2 = this.exchangeRateHistoryLoader.ladXmlDataFromInternet();
            this.exchangeRateHistoryLoader.loadXmlDataToDB(result2);

            list = this.exchangeRateHistoryRepository.findByCurrencyId(currencyId);
        }
        Collections.reverse(list);
        return list;
    }
}
