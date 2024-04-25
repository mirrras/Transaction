package com.example.transaction.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.transaction.model.Transaction;
import com.example.transaction.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionLimitExceededController {

    @Autowired
    private TransactionService transactionService;

    // Метод для получения списка транзакций, превысивших лимит
    @GetMapping("/exceeded")
    public ResponseEntity<List<Transaction>> getExceededTransactions() {
        // Реализация получения списка превышенных лимитов
        return ResponseEntity.ok().build();
    }

    // Другие методы контроллера для работы с транзакциями, превысившими лимит
}
