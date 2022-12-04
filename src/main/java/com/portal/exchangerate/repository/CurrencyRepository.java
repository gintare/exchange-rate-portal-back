package com.portal.exchangerate.repository;

import com.portal.exchangerate.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    @Query("SELECT c FROM Currency c WHERE c.name=?1")
    List<Currency> findByName(String name);
}
