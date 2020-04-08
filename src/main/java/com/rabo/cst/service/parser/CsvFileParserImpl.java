package com.rabo.cst.service.parser;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.rabo.cst.utils.StatementProcessingConstants.EXCEPTION_IN_PROCESSING_CSV_FILE;
import static com.rabo.cst.utils.StatementProcessingConstants.PROCESSING_CSV_FILE;

/**
 * Created by mravindran on 08/04/20.
 *
 * Parser used for  CSV files
 */
@Service
public class CsvFileParserImpl implements FileParser {

    private static final Logger LOG = LoggerFactory.getLogger(CsvFileParserImpl.class);

    private static final String REFERENCE = "Reference";
    private static final String ACCOUNT_NUMBER = "AccountNumber";
    private static final String DESCRIPTION = "Description";
    private static final String START_BALANCE = "Start Balance";
    private static final String MUTATION = "Mutation";
    private static final String END_BALANCE = "End Balance";
    private static final long CSV_HEADER_LINE_NUMBER = 1;
    private static final String[] CSV_DEFAULT_HEADER_MAPPING = { REFERENCE, ACCOUNT_NUMBER,
            DESCRIPTION, START_BALANCE, MUTATION, END_BALANCE };

    /***
     * Method to parse the CSV multipart file and get the transactions from the uploaded file
     * @param file of type MultipartFile {@link MultipartFile}
     * @return List of TransactionRecord {@link TransactionRecord}
     * @throws FileParserException
     */
    @Override
    public List<TransactionRecord> parse(MultipartFile file) throws FileParserException {
        List<TransactionRecord> transactionRecords;
        LOG.info(PROCESSING_CSV_FILE);
        try(Reader fileReader = new InputStreamReader(file.getInputStream())) {
            List<CSVRecord> csvRecord = CSVFormat.DEFAULT
                    .withHeader(CSV_DEFAULT_HEADER_MAPPING).parse(fileReader).getRecords();
            transactionRecords = csvRecord.stream().skip(CSV_HEADER_LINE_NUMBER)
                    .map(getTransactionRecordFunction())
                    .collect(Collectors.toList());
            return transactionRecords;
        } catch (Exception ex) {
            LOG.error(EXCEPTION_IN_PROCESSING_CSV_FILE);
            throw new FileParserException(EXCEPTION_IN_PROCESSING_CSV_FILE ,ex);
        }
    }

    private Function<CSVRecord, TransactionRecord> getTransactionRecordFunction() {
        return record -> TransactionRecord
                .builder()
                .reference(Long.valueOf(record.get(REFERENCE)))
                .accountNumber(record.get(ACCOUNT_NUMBER))
                .description(record.get(DESCRIPTION))
                .startBalance(new BigDecimal(record.get(START_BALANCE)))
                .mutation(new BigDecimal(record.get(MUTATION)))
                .endBalance(new BigDecimal(record.get(END_BALANCE)))
                .build();
    }
}
