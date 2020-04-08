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
public class XmlFileParserImplTest {

    @InjectMocks
    XmlFileParserImpl xmlFileParser;

    MockMultipartFile file;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void whenFileUploaded_XMLParserParses_ThenExtractRecords() throws FileParserException, IOException {
        getMockFile("records.xml", "application/xml", this.getClass().getClassLoader()
                .getResourceAsStream("records.xml"));
        List<TransactionRecord> transactionRecords = xmlFileParser.parse(file);
        assertEquals(10, transactionRecords.size());
        MathContext mx = new MathContext(4);
        assertEquals(Long.valueOf(108366), transactionRecords.get(0).getReference());
        assertEquals("NL91RABO0315273637", transactionRecords.get(1).getAccountNumber());
        assertEquals("Candy for Vincent Theuß", transactionRecords.get(3).getDescription());
        assertEquals(new BigDecimal(92.73).round(mx), transactionRecords.get(4).getStartBalance());
        assertEquals(new BigDecimal(27.8).round(mx).stripTrailingZeros(), transactionRecords.get(5).getMutation());
        assertEquals(new BigDecimal(27.95).round(mx), transactionRecords.get(9).getEndBalance());
    }

    @Test(expected = FileParserException.class)
    public void whenCSVFileUploaded_XMLParserParses_ThenThrowFileParserException() throws FileParserException, IOException {
        getMockFile("records.csv", "text/csv", this.getClass().getClassLoader()
                .getResourceAsStream("records.csv"));
        List<TransactionRecord> transactionRecords = xmlFileParser.parse(file);
    }

    /*This case doesn't work since we don't have a schema(xsd) verification if we have schema verification then the xml parsing
    @Test(expected = FileParserException.class)
    public void whenWrongFormatXMLFileUploaded_XMLParserParses_ThenThrowFileParserException() throws FileParserException, IOException {
        getMockFile("records_wrongFormat.xml", "application/xml", this.getClass().getClassLoader()
                .getResourceAsStream("records_wrongFormat.xml"));
        List<TransactionRecord> transactionRecords = xmlFileParser.parse(file);
    }*/

    private void getMockFile(String name, String contentType, InputStream resourceAsStream) throws IOException {
        file = new MockMultipartFile(name, name, contentType,
                resourceAsStream);
    }
}
