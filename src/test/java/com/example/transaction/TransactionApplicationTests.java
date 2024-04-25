package com.example.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.transaction.dto.TransactionDTO;
import com.example.transaction.model.LimitSetting;
import com.example.transaction.model.Transaction;
import com.example.transaction.repository.LimitSettingRepository;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.TransactionService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @Test
    void contextLoads() {
    }
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LimitSettingRepository limitSettingRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTransaction_LimitExceeded() {
        // Mock limit setting
        LimitSetting mockLimitSetting = new LimitSetting();
        mockLimitSetting.setCategory("Food");
        mockLimitSetting.setLimitAmount(BigDecimal.valueOf(100));

        // Mock transaction DTO exceeding the limit
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setCategory("Food");
        transactionDTO.setAmount(BigDecimal.valueOf(120)); // Exceeds the limit

        // Configure mock repository behavior
        when(limitSettingRepository.findByCategory("Food")).thenReturn(mockLimitSetting);

        // Test createTransaction method
        boolean result = transactionService.createTransaction(transactionDTO);

        assertTrue(result); // Expecting the transaction to have exceeded the limit
    }

    @Test
    public void testCreateTransaction_LimitNotExceeded() {
        // Mock limit setting
        LimitSetting mockLimitSetting = new LimitSetting();
        mockLimitSetting.setCategory("Food");
        mockLimitSetting.setLimitAmount(BigDecimal.valueOf(100));

        // Mock transaction DTO within the limit
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setCategory("Food");
        transactionDTO.setAmount(BigDecimal.valueOf(80)); // Within the limit

        // Configure mock repository behavior
        when(limitSettingRepository.findByCategory("Food")).thenReturn(mockLimitSetting);

        // Test createTransaction method
        boolean result = transactionService.createTransaction(transactionDTO);

        assertTrue(!result); // Expecting the transaction not to have exceeded the limit
    }

}

