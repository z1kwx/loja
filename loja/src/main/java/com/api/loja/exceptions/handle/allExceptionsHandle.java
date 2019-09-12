package com.api.loja.exceptions.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.loja.exceptions.AlreadyExistsExcpetion;
import com.api.loja.exceptions.InvalidAgencyException;
import com.api.loja.exceptions.InvalidProductException;
import com.api.loja.exceptions.InvalidStockQuantity;
import com.api.loja.exceptions.NotFoundException;
import com.api.loja.exceptions.UnathorizedDeleteException;

@RestControllerAdvice
public class allExceptionsHandle {

	private static final Logger log = LoggerFactory.getILoggerFactory().getLogger(allExceptionsHandle.class.getName());
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
		log.info("handleNotFoundException - start");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidAgencyException.class)
	public ResponseEntity<Object> handleInvalidAgencyExcption(InvalidAgencyException e){
		log.info("handleInvalidAgencyExcption - start");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidProductException.class)
	public ResponseEntity<Object> handleInvalidProductException(InvalidProductException e){
		log.info("handleInvalidProductException - start");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(AlreadyExistsExcpetion.class)
	public ResponseEntity<Object> handleAlreadyExistsExcpetion(AlreadyExistsExcpetion e){
		log.info("handleAlreadyExistsExcpetion - start");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(InvalidStockQuantity.class)
	public ResponseEntity<Object> handleInvalidStockQuantity(InvalidStockQuantity e){
		log.info("handleInvalidStockQuantity - start");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(UnathorizedDeleteException.class)
	public ResponseEntity<Object> handleUnathorizedDeleteException(UnathorizedDeleteException e){
		log.info("handleUnathorizedDeleteException - start");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	
	
}
