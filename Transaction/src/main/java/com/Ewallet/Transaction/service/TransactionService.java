package com.Ewallet.Transaction.service;

import com.Ewallet.Transaction.service.resource.TransactionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.Transaction;

public interface TransactionService {

	public boolean performTransaction(Long userId, TransactionRequest transactionRequest) throws JsonProcessingException;

}
