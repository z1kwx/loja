package com.api.loja.exceptions;

public class InvalidStockQuantity extends RuntimeException {

	private static final long serialVersionUID = 4344560497983883918L;

	public InvalidStockQuantity(String msg) {
		super(msg);
	}
	
	public InvalidStockQuantity() {
		super();
	}
}
