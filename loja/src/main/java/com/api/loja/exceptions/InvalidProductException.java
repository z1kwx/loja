package com.api.loja.exceptions;

public class InvalidProductException extends RuntimeException{

	private static final long serialVersionUID = -3340146733365512950L;

	public InvalidProductException(String msg) {
		super(msg);
	}
	
	public InvalidProductException() {
		super();
	}
	
}
