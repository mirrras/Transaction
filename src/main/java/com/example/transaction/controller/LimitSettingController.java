package com.example.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.transaction.model.LimitSetting;
import com.example.transaction.service.LimitSettingService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/limits")
public class LimitSettingController {

    @Autowired
    private LimitSettingService limitSettingService;

    // Метод для установки нового лимита
    @PostMapping("/set")
    public ResponseEntity<LimitSetting> setLimit(@RequestParam String category,
                                                 @RequestParam BigDecimal limitAmount,
                                                 @RequestParam String currency) {
        LimitSetting savedLimitSetting = limitSettingService.setNewLimit(category, limitAmount, currency);
        return ResponseEntity.ok(savedLimitSetting);
    }

    @GetMapping("/get")
    public ResponseEntity<LimitSetting> getLimit() {
        List<LimitSetting> limits = limitSettingService.getAllLimits();
        return ResponseEntity.ok((LimitSetting) limits);
    }

    // Другие методы контроллера для работы с лимитами
}


