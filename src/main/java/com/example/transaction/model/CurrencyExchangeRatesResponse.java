package com.example.transaction.model;

import java.util.List;

public class CurrencyExchangeRatesResponse {
    private List<CurrencyExchangeRate> rates;

    public CurrencyExchangeRatesResponse(List<CurrencyExchangeRate> rates) {
        this.rates = rates;
    }

    public List<CurrencyExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<CurrencyExchangeRate> rates) {
        this.rates = rates;
    }
}

