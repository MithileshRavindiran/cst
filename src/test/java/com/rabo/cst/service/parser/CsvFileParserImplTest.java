package com.rabo.cst.service.parser;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mravindran on 08/04/20.
 */
public class CsvFileParserImplTest {

    @InjectMocks
    CsvFileParserImpl  csvFileParser;

    MockMultipartFile file;


    @Before
    public void setup()  {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFileUploaded_CSVParserParses_ThenExtractRecords() throws FileParserException, IOException {
        getMockFile("records.csv", "text/csv", this.getClass().getClassLoader()
                .getResourceAsStream("records.csv"));
        List<TransactionRecord>  transactionRecords = csvFileParser.parse(file);
        assertEquals(10, transactionRecords.size());
        MathContext mx = new MathContext(4);
        assertEquals(Long.valueOf(132843l), transactionRecords.get(0).getReference());
        assertEquals("NL32RABO0195610843", transactionRecords.get(1).getAccountNumber());
        assertEquals("Tickets for Erik de Vries", transactionRecords.get(3).getDescription());
        assertEquals(new BigDecimal(14.81).round(mx), transactionRecords.get(4).getStartBalance());
        assertEquals(new BigDecimal(23.07).round(mx), transactionRecords.get(5).getMutation());
        assertEquals(new BigDecimal(80.53).round(mx), transactionRecords.get(9).getEndBalance());
    }

    @Test(expected = FileParserException.class)
    public void whenXmlFileUploaded_CSVParserParses_ThenThrowsFileParserException() throws FileParserException, IOException {
        file = new MockMultipartFile("records.xml","records.xml","application/xml",
                this.getClass().getClassLoader()
                        .getResourceAsStream("records.xml"));
        List<TransactionRecord>  transactionRecords = csvFileParser.parse(file);

    }

    @Test(expected = FileParserException.class)
    public void whenWrongFormattedCsvFileUploaded_CSVParserParses_ThenThrowsFileParserException() throws FileParserException, IOException {
        getMockFile("records_wrongFormat.csv", "text/csv", this.getClass().getClassLoader()
                .getResourceAsStream("records_wrongFormat.csv"));
        List<TransactionRecord>  transactionRecords = csvFileParser.parse(file);

    }

    private void getMockFile(String name, String contentType, InputStream resourceAsStream) throws IOException {
        file = new MockMultipartFile(name, name, contentType,
                resourceAsStream);
    }


}
