package com.example.transaction.repository;

import com.example.transaction.model.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {
    CurrencyExchangeRate findByCurrencyPair(String currencyPair);
    CurrencyExchangeRate findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);

}
