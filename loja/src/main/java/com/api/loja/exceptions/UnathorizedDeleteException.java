package com.api.loja.exceptions;

public class UnathorizedDeleteException extends RuntimeException{

	private static final long serialVersionUID = -3096910414707131345L;

	public UnathorizedDeleteException(String msg) {
		super(msg);
	}
	
	public UnathorizedDeleteException() {
		super();
	}
}
