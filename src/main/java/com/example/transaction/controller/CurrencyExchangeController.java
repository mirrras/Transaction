package com.example.transaction.controller;

import com.example.transaction.model.CurrencyExchangeRate;
import com.example.transaction.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange")
public class CurrencyExchangeController {

    private final CurrencyExchangeService exchangeService;

    @Autowired
    public CurrencyExchangeController(CurrencyExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/update-rates")
    public ResponseEntity<String> updateExchangeRates() {
        exchangeService.fetchAndSaveExchangeRates();
        return ResponseEntity.ok("Exchange rates updated successfully");
    }

    @GetMapping("/all-rates")
    public ResponseEntity<List<CurrencyExchangeRate>> getAllExchangeRates() {
        List<CurrencyExchangeRate> exchangeRates = exchangeService.getAllExchangeRates();
        return ResponseEntity.ok(exchangeRates);
    }
    @GetMapping("/rate")
    public ResponseEntity<CurrencyExchangeRate> getExchangeRateByCurrencyPair(@RequestParam String sourceCurrency,
                                                                              @RequestParam String targetCurrency) {
        CurrencyExchangeRate exchangeRate = exchangeService.getExchangeRateByCurrencyPair(sourceCurrency, targetCurrency);
        if (exchangeRate != null) {
            return ResponseEntity.ok(exchangeRate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

