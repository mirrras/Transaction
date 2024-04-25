package com.example.transaction.service;
import com.example.transaction.dto.TransactionDTO;
import com.example.transaction.model.CurrencyExchangeRate;
import com.example.transaction.model.LimitSetting;
import com.example.transaction.model.Transaction;
import com.example.transaction.repository.CurrencyExchangeRateRepository;
import com.example.transaction.repository.LimitSettingRepository;
import com.example.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final LimitSettingRepository limitSettingRepository;
    private final CurrencyExchangeRateRepository exchangeRateRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              LimitSettingRepository limitSettingRepository,
                              CurrencyExchangeRateRepository exchangeRateRepository) {
        this.transactionRepository = transactionRepository;
        this.limitSettingRepository = limitSettingRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Transactional
    public boolean createTransaction(TransactionDTO transactionDTO) {
        BigDecimal amountUSD = calculateTransactionAmountInUSD(transactionDTO);
        transactionDTO.setAmountUSD(amountUSD);

        Transaction transaction = convertToTransactionEntity(transactionDTO);

        checkAndSetLimitExceeded(transaction);

        transactionRepository.save(transaction);

        return transaction.isLimitExceeded();
    }
    private void checkAndSetLimitExceeded(Transaction transaction) {
        LimitSetting limitSetting = limitSettingRepository.findByCategoryAndExpenseType(transaction.getCategory(), transaction.getExpenseType());

        if (limitSetting != null && transaction.getAmount().compareTo(limitSetting.getLimitAmount()) > 0) {
            transaction.setLimitExceeded(true);
        }
    }
    public BigDecimal calculateTransactionAmountInUSD(TransactionDTO transactionDTO) {
        String transactionCurrency = transactionDTO.getCurrency();
        BigDecimal transactionAmount = transactionDTO.getAmount();

        try {
            String currencyPair = transactionCurrency + "/USD";

            CurrencyExchangeRate exchangeRate = exchangeRateRepository.findByCurrencyPair(currencyPair);

            if (exchangeRate != null) {
                BigDecimal rate = exchangeRate.getExchangeRate();
                return transactionAmount.multiply(rate);
            } else {
                throw new RuntimeException("Exchange rate not available for " + transactionCurrency);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to calculate transaction amount in USD: " + ex.getMessage());
        }
    }

    public List<TransactionDTO> getAllTransactions() {
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction:transactions){
            transactionDTOS.add(convertToTransactionDTO(transaction));
        }
        return transactionDTOS;
    }

    public List<TransactionDTO> getAllTransactionsByDateRange(Date startDate, Date endDate) {
        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);
        return convertToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> findTransactionsByCategory(String category) {
        List<Transaction> transactions = transactionRepository.findByCategory(category);
        return convertToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> findTransactionsByExpenseType(String expenseType) {
        List<Transaction>transactions = transactionRepository.findByExpenseType(expenseType);
        return convertToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> getTransactionsWithLimitExceeded() {
        List<Transaction> transactions =  transactionRepository.findByLimitExceeded(true);
        return convertToTransactionDTOList(transactions);
    }
    public void setLimit(String category, BigDecimal limitAmount) {
        // Проверяем, существует ли уже лимит для данной категории
        LimitSetting existingLimit = limitSettingRepository.findByCategory(category);

        if (existingLimit != null) {
            // Если лимит уже существует, обновляем его значение
            existingLimit.setLimitAmount(limitAmount);
            limitSettingRepository.save(existingLimit);
        } else {
            // Если лимит не существует, создаем новую запись лимита
            LimitSetting newLimit = new LimitSetting();
            newLimit.setCategory(category);
            newLimit.setLimitAmount(limitAmount);
            limitSettingRepository.save(newLimit);
        }
    }

    private List<TransactionDTO> convertToTransactionDTOList(List<Transaction> transactions) {
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDTOs.add(convertToTransactionDTO(transaction));
        }
        return transactionDTOs;
    }
    private TransactionDTO convertToTransactionDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setAmountUSD(transaction.getAmountUSD());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setCategory(transaction.getCategory());
        transactionDTO.setExpenseType(transaction.getExpenseType());
        transactionDTO.setLimitExceeded(transaction.isLimitExceeded());
        transactionDTO.setClientName(transaction.getClientName());
        transactionDTO.setTransactionType(transaction.getTransactionType());

        return transactionDTO;
    }

    private Transaction convertToTransactionEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setAmountUSD(transactionDTO.getAmountUSD());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setCategory(transactionDTO.getCategory());
        transaction.setExpenseType(transactionDTO.getExpenseType());
        transaction.setLimitExceeded(transactionDTO.isLimitExceeded());
        transaction.setClientName(transactionDTO.getClientName());
        transaction.setTransactionType(transactionDTO.getTransactionType());

        return transaction;
    }
}
