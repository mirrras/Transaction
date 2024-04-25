package com.example.transaction.repository;

import com.example.transaction.model.LimitSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitSettingRepository extends JpaRepository<LimitSetting, Long> {

    // Метод для поиска лимита по категории и типу расходов
    LimitSetting findByCategoryAndExpenseType(String category, String expenseType);

    // Метод для поиска лимита по категории
    LimitSetting findByCategory(String category);

    // Дополнительные методы для поиска и доступа к данным

}

