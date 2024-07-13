package com.Ewallet.Wallet.config;

import com.Ewallet.Wallet.exception.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WalletControllerAdvice {

	@ExceptionHandler(WalletException.class)
	public ResponseEntity<?> walletExceptionHandler(WalletException e) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("type", e.getType());
		errorMap.put("message", e.getMessage());
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
	}


}
