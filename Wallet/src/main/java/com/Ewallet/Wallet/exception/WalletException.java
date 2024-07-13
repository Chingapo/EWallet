package com.Ewallet.Wallet.exception;

import lombok.Getter;

@Getter
public class WalletException extends RuntimeException{

	private String type;
	private String message;
	public WalletException(String type, String message) {
		this.type = type;
		this.message = message;
	}

}
