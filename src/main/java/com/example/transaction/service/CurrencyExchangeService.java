package com.example.transaction.service;

import com.example.transaction.model.CurrencyExchangeRate;
import com.example.transaction.model.CurrencyExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.transaction.repository.CurrencyExchangeRateRepository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;

@Service
public class CurrencyExchangeService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private CurrencyExchangeRateRepository exchangeRateRepository;

    private final String apiKey = "092ea06c62344ceea33552412ef0c72e";
    public void fetchAndSaveExchangeRates() {
        webClientBuilder.build()
                .get()
                .uri("https://api.twelvedata.com/currency_conversion")
                .header("apikey", apiKey)
                .retrieve()
                .bodyToMono(CurrencyExchangeRatesResponse.class)
                .subscribe(response -> {
                    // Обработка ответа и сохранение курсов валют в базу данных
                    for (CurrencyExchangeRate rate : response.getRates()) {
                        CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate();
                        exchangeRate.setSourceCurrency(rate.getSourceCurrency());
                        exchangeRate.setTargetCurrency(rate.getTargetCurrency());
                        exchangeRate.setExchangeRate(rate.getExchangeRate());
                        exchangeRate.setDate(new Date()); // Установка текущей даты
                        exchangeRateRepository.save(exchangeRate);
                    }
                });
    }
    @Scheduled(cron = "0 0 * * * *") // Запуск каждый час
    public void fetchAndSaveExchangeRatesPeriodically() {
        fetchAndSaveExchangeRates(); // Вызываем метод обновления курсов валют
    }

    public List<CurrencyExchangeRate> getAllExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    public CurrencyExchangeRate getExchangeRateByCurrencyPair(String sourceCurrency, String targetCurrency) {
        return exchangeRateRepository.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency);
    }
}

