package com.Ewallet.Wallet.controller;

import com.Ewallet.Wallet.domain.Wallet;
import com.Ewallet.Wallet.service.WalletService;
import com.Ewallet.Wallet.service.resource.WalletTransactionRequest;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {

	@Autowired
	WalletService walletService;

	@GetMapping("wallet/{user-id}")
	public ResponseEntity<?> getWallet(@PathVariable("user-id") String userId) {
		return new ResponseEntity<>(walletService.getWallet(Long.valueOf(userId)), HttpStatus.OK);
	}

	@PostMapping("wallet/transaction")
	public ResponseEntity<Boolean> performTransaction(@RequestBody WalletTransactionRequest walletTransactionRequest) {
		boolean success = walletService.performTransaction(walletTransactionRequest);
		return new ResponseEntity<>(success, HttpStatus.OK);
	}


}
