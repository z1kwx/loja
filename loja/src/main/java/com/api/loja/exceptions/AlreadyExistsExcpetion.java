package com.api.loja.exceptions;

public class AlreadyExistsExcpetion extends RuntimeException {

	private static final long serialVersionUID = 5310375330932463170L;

	public AlreadyExistsExcpetion(String  msg) {
		super(msg);
	}
	
	public AlreadyExistsExcpetion() {
		super();
	}
}
