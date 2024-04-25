package com.example.transaction.dto;

import com.example.transaction.model.LimitSetting;
import com.example.transaction.model.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class CurrencyExchangeRateDTO {
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal exchangeRate;
    private Date date;

}

