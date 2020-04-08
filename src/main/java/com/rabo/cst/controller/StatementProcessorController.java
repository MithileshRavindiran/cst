package com.rabo.cst.controller;

import com.rabo.cst.domain.TransactionRecord;
import com.rabo.cst.exception.FileParserException;
import com.rabo.cst.service.TransactionValidator;
import com.rabo.cst.service.TransactionsExtractor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by mravindran on 08/04/20.
 */
@RestController
@RequestMapping("/v1/statement/")
@Api(value = "Customer Statement Processor",description = "Process the Customer Statements")
public class StatementProcessorController {

    @Autowired
    TransactionsExtractor transactionsExtractor;

    @Autowired
    TransactionValidator transactionValidator;


    @ApiOperation(value = "Upload the statements and retrieve the validated transactions with reasons",
            response = TransactionRecord.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Wrong format File uploaded/Exception while processing files"),
            @ApiResponse(code = 500, message = "Internal Server Exception")
    })
    @PostMapping(path = "/upload", produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<List<TransactionRecord>> uploadStatementsForValidation(@RequestParam(value = "file") MultipartFile file)
            throws FileParserException {
        transactionValidator.validateTransactions(transactionsExtractor.extractTransactions(file));
            return ResponseEntity.
                    ok().
                    body(transactionValidator.validateTransactions(transactionsExtractor.extractTransactions(file)));

    }

}
