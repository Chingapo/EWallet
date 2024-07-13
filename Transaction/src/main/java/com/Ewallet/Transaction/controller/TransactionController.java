package com.Ewallet.Transaction.controller;

import com.Ewallet.Transaction.service.TransactionService;
import com.Ewallet.Transaction.service.resource.TransactionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PostMapping("/transaction/{user-id}")
	public ResponseEntity<Boolean> transaction(@PathVariable("user-id") Long userId,@RequestBody TransactionRequest transactionRequest) throws JsonProcessingException {
		transactionService.performTransaction(userId, transactionRequest);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
