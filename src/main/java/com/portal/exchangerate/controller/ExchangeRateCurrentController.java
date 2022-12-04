package com.portal.exchangerate.controller;

import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.model.ExchangeRateCurrent;
import com.portal.exchangerate.repository.ExchangeRateCurrentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v2/exchange_rate_current")
public class ExchangeRateCurrentController {

    @Autowired
    private final ExchangeRateCurrentRepository exchangeRateCurrentRepository = null;

    @GetMapping
    public List<ExchangeRateCurrent> getCurrency(){
        return exchangeRateCurrentRepository.findAll();
    }
}
