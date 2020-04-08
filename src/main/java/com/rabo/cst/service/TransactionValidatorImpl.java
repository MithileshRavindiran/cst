package com.rabo.cst.service;

import com.rabo.cst.domain.TransactionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.MathContext;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static com.rabo.cst.utils.StatementProcessingConstants.*;

/**
 * Created by mravindran on 08/04/20.
 *
 * Validate transactions and add validation messages to the transactions
 */
@Service
public class TransactionValidatorImpl implements TransactionValidator {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionValidatorImpl.class);


    /***
     * Method to  validate  the  transactions rules  used now
     * i) Validate balance check startBalance+mutation == endBalance
     * ii) Reference of the transactions should  be unique
     * @param transactionRecords of type TransactionRecord {@link TransactionRecord}
     * @return List of transactionRecords {@link TransactionRecord}
     */
    @Override
    public List<TransactionRecord> validateTransactions(List<TransactionRecord> transactionRecords)  {
            List<TransactionRecord> transactionRecordList = null;
            if (!transactionRecords.isEmpty()) {
                LOG.info(TRANSACTION_VALIDATION);
                transactionRecordList = transactionRecords;
                transactionRecordList.forEach(checkValidBalance(transactionRecordList));
            }
            return transactionRecordList;
    }

    private Consumer<TransactionRecord> checkValidBalance(List<TransactionRecord> transactionRecordList) {
        return transaction -> {
            transaction.setIsInvalidRecord(Boolean.FALSE);
            if (!isValidBalance(transaction)) {
                transaction.setIsInvalidRecord(Boolean.TRUE);
                transaction.setReason(WRONG_END_BALANCE);
        }
            if (!transaction.getIsInvalidRecord()  && isDuplicateReference(transactionRecordList, transaction)) {
                transaction.setIsInvalidRecord(Boolean.TRUE);
                transaction.setReason(DUPLICATE_TRANSACTION_REFERENCE);
            }
        };
    }

    private boolean isDuplicateReference(List<TransactionRecord> transactionRecordList, TransactionRecord transaction) {
       return Collections.frequency(transactionRecordList, transaction) > 1;
    }


    private boolean isValidBalance(TransactionRecord transaction) {
        MathContext mx = new MathContext(4);
        return transaction.getEndBalance().subtract(transaction.getStartBalance()).round(mx).stripTrailingZeros()
                .equals(transaction.getMutation().round(mx).stripTrailingZeros());
    }
}
