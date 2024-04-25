package com.example.transaction.dto;

import com.example.transaction.model.LimitSetting;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class TransactionDTO {

    private Long id;
    private BigDecimal amount;
    private BigDecimal amountUSD;
    private String currency;
    private Date transactionDate;
    private String category;
    private String expenseType;
    private boolean limitExceeded;
    private String clientName;
    private String transactionType;
}

