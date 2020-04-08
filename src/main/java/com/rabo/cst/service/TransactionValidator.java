package com.rabo.cst.service;

import com.rabo.cst.domain.TransactionRecord;

import java.util.List;

/**
 * Created by mravindran on 08/04/20.
 */
public interface TransactionValidator {

    List<TransactionRecord> validateTransactions(List<TransactionRecord> transactionRecords);
}
