package com.example.transaction.controller;

import com.example.transaction.dto.TransactionDTO;
import com.example.transaction.model.Transaction;
import com.example.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
//@Api(tags = "Transaction Management", description = "Endpoints for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
//    @ApiOperation("Create a new transaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setAmountUSD(transactionService.calculateTransactionAmountInUSD(transactionDTO));
        boolean created = transactionService.createTransaction(transactionDTO);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Transaction created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create transaction");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/byDateRange")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<TransactionDTO> transactionsDTO = transactionService.getAllTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactionsDTO);
    }
    @GetMapping("/byCategory/{category}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCategory(@PathVariable String category) {
        List<TransactionDTO> transactions = transactionService.findTransactionsByCategory(category);
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/byExpenseType/{expenseType}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByExpenseType(@PathVariable String expenseType) {
        List<TransactionDTO> transactions = transactionService.findTransactionsByExpenseType(expenseType);
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/limitExceeded")
    public ResponseEntity<List<TransactionDTO>> getTransactionsWithLimitExceeded() {
        List<TransactionDTO> transactions = transactionService.getTransactionsWithLimitExceeded();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/setLimit")
    public ResponseEntity<String> setLimit(@RequestParam String category, @RequestParam BigDecimal limitAmount) {
        transactionService.setLimit(category, limitAmount);
        return ResponseEntity.ok("Limit set successfully");
    }

}
