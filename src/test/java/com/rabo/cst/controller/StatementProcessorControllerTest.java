package com.rabo.cst.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import com.rabo.cst.exception.GlobalExceptionHandler;
import com.rabo.cst.service.TransactionValidator;
import com.rabo.cst.service.TransactionsExtractor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.rabo.cst.util.TestHelperUtil.createTransactionRecord;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by mravindran on 08/04/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class StatementProcessorControllerTest {


    private MockMvc mockMvc;


    @Mock
    TransactionsExtractor transactionsExtractor;

    @Mock
    TransactionValidator transactionValidator;

    @InjectMocks
    StatementProcessorController statementProcessorController;

    List<TransactionRecord> transactionsList = new ArrayList<>();

    MockMultipartFile file;

    ObjectMapper mapper;

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(
                    HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(
                        GlobalExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(
                        new GlobalExceptionHandler(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(statementProcessorController)
                .setHandlerExceptionResolvers(createExceptionResolver())
                .build();
        transactionsList = getTransactionRecords();
        mapper = new ObjectMapper();
    }

    @Test
    public void whenCSVFileUploaded_ControllerProcess_andThenReturnListOfTransactionsWithReasons() throws Exception {

        file = new MockMultipartFile("records.csv","records.csv","text/csv",
                this.getClass().getClassLoader()
                        .getResourceAsStream("records.csv"));
        when(this.transactionsExtractor.extractTransactions(any())).thenReturn(transactionsList);

        when(this.transactionValidator.validateTransactions(any())).thenReturn(transactionsList);

        MvcResult result = this.mockMvc.perform(multipart("/v1/statement/upload").file("file", file.getBytes())).andExpect(status().isOk())
                .andReturn();

        assertEquals(mapper.writeValueAsString(transactionsList), result.getResponse().getContentAsString());
    }

    @Test
    public void whenXMLFileUploaded_ControllerProcess_andThenReturnListOfTransactionsWithReasons() throws Exception {

        file = new MockMultipartFile("records.xml","records.xml","application/xml",
                this.getClass().getClassLoader()
                        .getResourceAsStream("records.xml"));
        when(this.transactionsExtractor.extractTransactions(any())).thenReturn(transactionsList);

        when(this.transactionValidator.validateTransactions(any())).thenReturn(transactionsList);

        MvcResult result = this.mockMvc.perform(multipart("/v1/statement/upload").file("file", file.getBytes())).andExpect(status().isOk())
                .andReturn();

        assertEquals(mapper.writeValueAsString(transactionsList), result.getResponse().getContentAsString());
    }

    @Test
    public void whenXMLFileUploaded_ControllerProcess_thenExtractorThrowsFileParserException_andThenControllerThrowsBadRequestExceptionFromHandler() throws Exception {

        file = new MockMultipartFile("records.xml","records.xml","application/xml",
                this.getClass().getClassLoader()
                        .getResourceAsStream("records.xml"));
        when(this.transactionsExtractor.extractTransactions(any())).thenThrow(new FileParserException("Exception in parsing"));

        when(this.transactionValidator.validateTransactions(any())).thenReturn(transactionsList);

        this.mockMvc.perform(multipart("/v1/statement/upload").file("file", file.getBytes())).andExpect(status().isBadRequest());


    }

    @Test
    public void whenXMLFileUploaded_ControllerProcess_thenValidatorThrowsGenericException_andThenControllerThrowsInternalServerExceptionFromHandler() throws Exception {

        file = new MockMultipartFile("records.xml","records.xml","application/xml",
                this.getClass().getClassLoader()
                        .getResourceAsStream("records.xml"));
        when(this.transactionsExtractor.extractTransactions(any())).thenReturn(transactionsList);

        when(this.transactionValidator.validateTransactions(any())).thenThrow(new IllegalArgumentException());

        this.mockMvc.perform(multipart("/v1/statement/upload").file("file", file.getBytes())).andExpect(status().isInternalServerError());


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
