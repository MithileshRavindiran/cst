package com.rabo.cst.service;

import com.rabo.cst.domain.TransactionRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.rabo.cst.util.TestHelperUtil.createTransactionRecord;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by mravindran on 08/04/20.
 */
public class TransactionValidatorImplTest {

    @InjectMocks
    TransactionValidatorImpl transactionValidator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void whenTransactionsRecordsAreSend_ValidatorValidates_thenIndicatesTheTransactionsWhenNotValid() {
        List<TransactionRecord> transactionRecordsResult = transactionValidator.validateTransactions(getTransactionRecords());
        assertNotNull(transactionRecordsResult);
        assertFalse(transactionRecordsResult.get(0).getIsInvalidRecord());
        assertFalse(transactionRecordsResult.get(1).getIsInvalidRecord());
        assertNull(transactionRecordsResult.get(0).getReason());
        assertNull(transactionRecordsResult.get(1).getReason());
        assertTrue(transactionRecordsResult.get(2).getIsInvalidRecord());
        assertEquals("Transaction Balance Discrepancy", transactionRecordsResult.get(2).getReason());
        assertTrue(transactionRecordsResult.get(3).getIsInvalidRecord());
        assertEquals("Transaction Balance Discrepancy", transactionRecordsResult.get(3).getReason());
        assertTrue(transactionRecordsResult.get(4).getIsInvalidRecord());
        assertEquals("Duplicate Transaction References", transactionRecordsResult.get(4).getReason());

    }


    private List<TransactionRecord> getTransactionRecords() {
        List<TransactionRecord> transactionRecords = new ArrayList<>();
        transactionRecords.add(createTransactionRecord("1234", "1234", false, "Birthday cards",
                new BigDecimal(10000), new BigDecimal(+4500), new BigDecimal(14500)));
        transactionRecords.add(createTransactionRecord("1235","1234", false, "Marriage cards",
                new BigDecimal(110), new BigDecimal(+45), new BigDecimal(155)));
        transactionRecords.add(createTransactionRecord("1236","1234", false, "Marriage cards",
                new BigDecimal(110), new BigDecimal(+45), new BigDecimal(165)));
        transactionRecords.add(createTransactionRecord("1237","1234", false, "Marriage cards",
                new BigDecimal(110), new BigDecimal(+45), new BigDecimal(175)));
        transactionRecords.add(createTransactionRecord("1237","1234", false, "Marriage cards",
                new BigDecimal(21.79), new BigDecimal(+3.63), new BigDecimal(25.42)));
        return transactionRecords;
    }
}
