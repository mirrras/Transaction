package com.example.transaction.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "amount_usd")
    private BigDecimal amountUSD;

    @Column(name = "currency")
    private String currency;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "category")
    private String category;

    @Column(name = "expense_type")
    private String expenseType;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    @ManyToOne
    @JoinColumn(name = "limit_setting_id")
    private LimitSetting limitSetting;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "transaction_type")
    private String transactionType;


}
