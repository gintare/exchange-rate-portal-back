package com.portal.exchangerate.controller;

import com.portal.exchangerate.repository.CurrencyRepository;
import com.portal.exchangerate.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v2/currency")
public class CurrencyController {
    @Autowired
    private final CurrencyRepository currencyRepository = null;

    @GetMapping
    public List<Currency> getCurrency(){
        return currencyRepository.findAll();
    }

}
