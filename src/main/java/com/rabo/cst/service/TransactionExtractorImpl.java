package com.rabo.cst.service;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import com.rabo.cst.service.parser.factory.FileParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.rabo.cst.utils.StatementProcessingConstants.EXTRACTING_TRANSACTIONS_FROM_FILE;


/**
 * Created by mravindran on 08/04/20.
 *
 * Uses to extract the  transactions from the uploaded  file
 */
@Service
public class TransactionExtractorImpl implements TransactionsExtractor {

    public static final Logger LOG = LoggerFactory.getLogger(TransactionExtractorImpl.class);

    @Autowired
    private FileParserFactory fileParserFactory;


    /***
     * Method the extract  the transactions form the MultipartFile
     * @param multipartFile of type MultiPartFile {@link MultipartFile}
     * @return List of TransactionRecord {@link TransactionRecord}
     * @throws FileParserException
     */
    @Override
    public List<TransactionRecord> extractTransactions(MultipartFile multipartFile) throws FileParserException {

        try {
            String contentType = multipartFile.getContentType();
            LOG.info(EXTRACTING_TRANSACTIONS_FROM_FILE);
            return fileParserFactory.getFileParser(contentType).parse(multipartFile);
        } catch (FileParserException ex) {
           throw ex;
        }
    }


}
