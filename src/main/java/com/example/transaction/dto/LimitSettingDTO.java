package com.example.transaction.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class LimitSettingDTO {
    private Long id;
    private String category;
    private BigDecimal limitAmount;
    private String currency;
    private Date effectiveDate;
    private Date endDate;
    private String expenseType;
}

