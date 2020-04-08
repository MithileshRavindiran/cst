package com.rabo.cst.service.parser.factory;

import com.rabo.cst.exception.FileParserException;
import com.rabo.cst.service.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rabo.cst.utils.StatementProcessingConstants.*;

/**
 * Created by mravindran on 08/04/20.
 *
 * Factory class to choose the proper parser for the type of file being sent
 */
@Service
public class FileParserFactory {

    private static final Logger LOG = LoggerFactory.getLogger(FileParserFactory.class);

    @Autowired
    FileParser csvFileParserImpl;

    @Autowired
    FileParser xmlFileParserImpl;

    public FileParser getFileParser(String fileType) throws FileParserException {
        LOG.info(FILE_PARSER_FETCH_THE_UPLOADED_FILE);
        switch (fileType) {
            case CONTENT_TYPE_TEXT_CSV:
                return csvFileParserImpl;
            case CONTENT_TYPE_TEXT_XML:
            case CONTENT_TYPE_APPLICATION_XML:
                return xmlFileParserImpl;
            default:
                throw new FileParserException(EXCEPTION_UNSUPPORTED_FILE_TYPE);
        }
    }
}
