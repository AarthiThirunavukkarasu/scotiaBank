package com.scotiaBank.ScotiaBank.Exception;

public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException(String message) {
        super(message);
    }
}
