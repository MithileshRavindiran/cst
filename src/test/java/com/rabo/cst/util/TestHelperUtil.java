package com.rabo.cst.util;

import com.rabo.cst.domain.TransactionRecord;

import java.math.BigDecimal;

/**
 * Created by mravindran on 08/04/20.
 */
public final class TestHelperUtil {

    public static TransactionRecord createTransactionRecord(String reference, String accountNumber, boolean  isInvalidRecord, String description,
                                                      BigDecimal startBalance, BigDecimal mutation, BigDecimal endBalance) {
        return TransactionRecord
                .builder()
                .reference(Long.valueOf(reference))
                .accountNumber(accountNumber)
                .isInvalidRecord(isInvalidRecord)
                .description(description)
                .startBalance(startBalance)
                .mutation(mutation)
                .endBalance(endBalance).build();
    }
}
