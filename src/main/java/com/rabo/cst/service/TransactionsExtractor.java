package com.rabo.cst.service;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mravindran on 08/04/20.
 */
public interface TransactionsExtractor {

    /***
     * Method the extract  the transactions form the MultipartFile
     * @param file of type MultiPartFile {@link MultipartFile}
     * @return List of TransactionRecord {@link TransactionRecord}
     * @throws FileParserException
     */
    List<TransactionRecord> extractTransactions(MultipartFile file) throws FileParserException;
}
