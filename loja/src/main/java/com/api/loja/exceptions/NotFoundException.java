package com.api.loja.exceptions;

public class NotFoundException extends RuntimeException{

	
	static final long serialVersionUID = -3487918234082883212L;


	public NotFoundException(String msg) {
		super(msg);
	}
	
	public NotFoundException() {
		super();
	}

}
