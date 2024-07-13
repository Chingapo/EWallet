package com.Ewallet.User.exception;


import lombok.Getter;

@Getter
public class UserException extends RuntimeException{

	private String type;
	private String message;
	public UserException(String type, String message) {
		this.type = type;
		this.message = message;
	}

}
