package com.example.transaction.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "limit_settings")
public class LimitSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "limit_amount")
    private BigDecimal limitAmount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "expense_type")
    private String expenseType;
}
