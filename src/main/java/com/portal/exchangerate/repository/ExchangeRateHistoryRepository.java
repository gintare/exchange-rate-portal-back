package com.portal.exchangerate.repository;

import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.model.ExchangeRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeRateHistoryRepository extends JpaRepository<ExchangeRateHistory, Integer> {
    @Query("SELECT c FROM ExchangeRateHistory c WHERE c.currencyId=?1")
    List<ExchangeRateHistory> findByCurrencyId(int currencyId);
}
