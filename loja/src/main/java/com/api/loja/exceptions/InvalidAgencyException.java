package com.api.loja.exceptions;

public class InvalidAgencyException extends RuntimeException {

	private static final long serialVersionUID = -3676821871895182381L;
	
	
	public InvalidAgencyException(String msg) {
		super(msg);
	}

	public InvalidAgencyException() {
		super();
	}
	
}
