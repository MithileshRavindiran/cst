package com.rabo.cst.service.parser;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.domain.Transactions;
import com.rabo.cst.exception.FileParserException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.rabo.cst.utils.StatementProcessingConstants.*;

/**
 * Created by mravindran on 08/04/20.
 *
 * Parser used  for XML files
 */
@Service
public class XmlFileParserImpl implements FileParser {

    private static final Logger LOG = LoggerFactory.getLogger(XmlFileParserImpl.class);


    /***
     * Method to parse the XML multipart file and get the transactions from the uploaded file
     * @param file of type MultipartFile {@link MultipartFile}
     * @return List of TransactionRecord {@link TransactionRecord}
     * @throws FileParserException
     */
    @Override
    public List<TransactionRecord> parse(MultipartFile file) throws FileParserException {
        Transactions transactionsRecords;
        JAXBContext jaxbContext;
        LOG.info(PROCESSING_XML_FILE);
        try {
            jaxbContext = JAXBContext.newInstance(Transactions.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            transactionsRecords = (Transactions) unmarshaller.unmarshal(file.getInputStream());
        } catch (Exception ex) {
            LOG.error(EXCEPTION_IN_PROCESSING_XML_FILE, ex);
            throw new FileParserException(ex);
        }
        return transactionsRecords.getTransactions();
    }

}
