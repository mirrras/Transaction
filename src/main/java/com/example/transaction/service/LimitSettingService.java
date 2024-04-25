package com.example.transaction.service;

import com.example.transaction.model.LimitSetting;
import com.example.transaction.repository.LimitSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LimitSettingService {

    private final LimitSettingRepository limitSettingRepository;

    @Autowired
    public LimitSettingService(LimitSettingRepository limitSettingRepository) {
        this.limitSettingRepository = limitSettingRepository;
    }


    public LimitSetting setNewLimit(String category, BigDecimal limitAmount, String currency) {
        Optional<LimitSetting> existingLimit = Optional.ofNullable(limitSettingRepository.findByCategory(category));

        if (existingLimit.isPresent()) {
            return existingLimit.get(); // Возвращаем существующий лимит, если он уже установлен
        }

        // Создаем новый лимит и сохраняем в базе данных
        LimitSetting newLimit = new LimitSetting();
        newLimit.setCategory(category);
        newLimit.setExpenseType(currency);
        newLimit.setLimitAmount(limitAmount);
        newLimit.setEffectiveDate(new Date());
        return limitSettingRepository.save(newLimit);
    }

    public List<LimitSetting> getAllLimits() {
        // Возвращает список всех установленных лимитов
        return limitSettingRepository.findAll();
    }


}

