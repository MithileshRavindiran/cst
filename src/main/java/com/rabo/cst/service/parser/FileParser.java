package com.rabo.cst.service.parser;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by mravindran on 08/04/20.
 */
public interface FileParser {

    /***
     * Method to parse the Uploaded multipart file and get the transactions from the uploaded file
     * @param file of type MultipartFile {@link MultipartFile}
     * @return List of TransactionRecord {@link TransactionRecord}
     * @throws FileParserException
     */
    List<TransactionRecord> parse(MultipartFile file) throws FileParserException;
}
