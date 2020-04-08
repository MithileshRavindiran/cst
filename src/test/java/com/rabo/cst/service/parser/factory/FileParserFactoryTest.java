package com.rabo.cst.service.parser.factory;

import com.rabo.cst.exception.FileParserException;
import com.rabo.cst.service.parser.CsvFileParserImpl;
import com.rabo.cst.service.parser.FileParser;
import com.rabo.cst.service.parser.XmlFileParserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by mravindran on 08/04/20.
 */
public class FileParserFactoryTest {

    @InjectMocks
    FileParserFactory fileParserFactory;

    @Mock
    CsvFileParserImpl csvFileParserImpl;


    @Mock
    XmlFileParserImpl xmlFileParserImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFileTypeCSV_FactoryClassProcesses_thenReturnsCSVParserFile() throws FileParserException {
        assertTrue(fileParserFactory.getFileParser("text/csv") instanceof CsvFileParserImpl);
    }

    @Test
    public void whenFileTypeXML_FactoryClassProcesses_thenReturnsXMLParserFile() throws FileParserException {
        assertTrue(fileParserFactory.getFileParser("application/xml") instanceof XmlFileParserImpl);
    }

    @Test
    public void whenFileTypeTextXML_FactoryClassProcesses_thenReturnsXMLParserFile() throws FileParserException {
        assertTrue(fileParserFactory.getFileParser("text/xml") instanceof XmlFileParserImpl);
    }

    @Test(expected = FileParserException.class)
    public void whenFileTypeImage_FactoryClassProcesses_thenThrowFileParserException() throws FileParserException {
        fileParserFactory.getFileParser("image");
    }


}
