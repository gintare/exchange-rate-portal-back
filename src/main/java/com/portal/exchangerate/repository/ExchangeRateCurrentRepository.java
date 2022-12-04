package com.portal.exchangerate.repository;

import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.model.ExchangeRateCurrent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangeRateCurrentRepository extends JpaRepository<ExchangeRateCurrent, Integer> {

/*
    @Query("SELECT * from EXCHANGE_RATE_CURRENT a, CURRENCY b WHERE a.CURRENCY_FK_ID = b.ID ")
    public String getJoinInformation();

 */


}
