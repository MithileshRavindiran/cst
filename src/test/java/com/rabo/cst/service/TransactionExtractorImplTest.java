package com.rabo.cst.service;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import com.rabo.cst.service.parser.FileParser;
import com.rabo.cst.service.parser.factory.FileParserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.rabo.cst.util.TestHelperUtil.createTransactionRecord;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by mravindran on 08/04/20.
 */
public class TransactionExtractorImplTest {

    @InjectMocks
    TransactionExtractorImpl transactionExtractor;

    @Mock
    FileParserFactory fileParserFactory;

    @Mock
    FileParser csvFileParserImpl;

    MockMultipartFile file;

    @Before
    public void  setup() {
        MockitoAnnotations.initMocks(this);
        file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
    }

    @Test
    public void whenfileUploaded_thenExtractTransactions_Succesfully() throws FileParserException {
        List<TransactionRecord> transactionRecords = getTransactionRecords();
        when(fileParserFactory.getFileParser(anyString())).thenReturn(csvFileParserImpl);
        when(csvFileParserImpl.parse(file)).thenReturn(transactionRecords);
        List<TransactionRecord>  resultTransactionRecords = transactionExtractor.extractTransactions(file);
        assertNotNull(resultTransactionRecords);
        assertEquals(2, resultTransactionRecords.size());
        assertEquals("1234", resultTransactionRecords.get(0).getAccountNumber());
        assertFalse(resultTransactionRecords.get(1).getIsInvalidRecord());
        assertEquals("Birthday cards", resultTransactionRecords.get(0).getDescription());
        assertEquals(new BigDecimal(110), resultTransactionRecords.get(1).getStartBalance());
    }

    @Test(expected = FileParserException.class)
    public void whenfileUploaded_thenFileParserException() throws FileParserException {
        when(fileParserFactory.getFileParser(anyString())).thenReturn(csvFileParserImpl);
        when(csvFileParserImpl.parse(file)).thenThrow(new FileParserException("Failed in parsing xml"));
        List<TransactionRecord>  resultTransactionRecords = transactionExtractor.extractTransactions(file);
    }

    private List<TransactionRecord> getTransactionRecords() {
        List<TransactionRecord> transactionRecords = new ArrayList<>();
        transactionRecords.add(createTransactionRecord("1234","1234", false, "Birthday cards",
                new BigDecimal(100), new BigDecimal(+45), new BigDecimal(145)));
        transactionRecords.add(createTransactionRecord("1235","1234", false, "Marriage cards",
                new BigDecimal(110), new BigDecimal(+45), new BigDecimal(155)));
        return transactionRecords;
    }


}
