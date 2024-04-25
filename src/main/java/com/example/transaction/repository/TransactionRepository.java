package com.example.transaction.repository;
import com.example.transaction.model.LimitSetting;
import com.example.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByLimitSettingAndAmountGreaterThan(LimitSetting limitSetting, BigDecimal amount);

    List<Transaction> findByLimitSetting(LimitSetting limitSetting);

    List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);

    List<Transaction> findByCategory(String category);

    List<Transaction> findByExpenseType(String expenseType);

    List<Transaction> findByLimitExceeded(boolean b);
}

