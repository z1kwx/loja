package com.api.loja.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.loja.models.Stock;
import com.api.loja.service.StockService;

@RestController
public class StockController {
	
	private static final Logger log = LoggerFactory.getILoggerFactory().getLogger(StockController.class.getName());

	@Autowired
	private StockService stockService;

	
	@GetMapping("/stock")
	public ResponseEntity<List<Stock>> getAllStock(){
		log.info("getAllStock() - start");
		List<Stock> allStocks = new ArrayList<>();
		allStocks = stockService.getAllStock();
		return ResponseEntity.status(HttpStatus.OK).body(allStocks);
	}
	
	@GetMapping("/stock/{id}")
	public ResponseEntity<Stock> getOneStockById(@PathVariable("id") Long id){
		log.info("getOneStockById(Long id) - start - param: id: {}", id);
		Stock stock = new Stock();
		stock = stockService.getOneStockById(id);
		return ResponseEntity.status(HttpStatus.OK).body(stock);
	}
	

	@GetMapping("/stock/agencia/{agenciaId}")
	public ResponseEntity<List<Stock>> getAllStocksByAgenId(@PathVariable("agenciaId") Long agenId){
		log.info("getAllStocksByAgenId(Long agenId) - start - param: agenciaId: {}", agenId);
		List<Stock> allAgenStock = new ArrayList<>();
		allAgenStock = stockService.getAllStocksByAgenciaId(agenId);
		return ResponseEntity.status(HttpStatus.OK).body(allAgenStock);
		}
	
	@GetMapping("/stock/product/{productId}")
	public ResponseEntity<List<Stock>> getAllStocksByProductId(@PathVariable("productId") Long productId){
		log.info("getAllStocksByProductId(Long productId) - start - param: productId: {}", productId);
		List<Stock> allProductsStock = new ArrayList<>();
		allProductsStock = stockService.getAllStocksByProductId(productId);
		return ResponseEntity.status(HttpStatus.OK).body(allProductsStock);
	}
	
	@GetMapping("/stock/agencia/{agenciaId}/product/{productId}")
	public ResponseEntity<Stock> getStockByAgenIdAndProductId(@PathVariable("agenciaId") Long agenciaId,
																	@PathVariable("productId") Long productId){
		log.info("getStockByAgenIdAndProductId(Long agenciaId, Long productId) - start - params: agenciaId: {}, productId {}", agenciaId, productId);
		Stock stock = new Stock();
		stock = stockService.getStockByAgenIdAndProcudctId(agenciaId, productId);
		return ResponseEntity.status(HttpStatus.OK).body(stock);
	}
	
	
	@PostMapping("/stock")
	public ResponseEntity<String> createStock(@RequestBody Stock stock){
		log.info("createStock(Stock stock) - start - param: ", stock);
		stockService.createStock(stock);
		return ResponseEntity.status(HttpStatus.CREATED).body("Stock cadastrado com sucesso!");
	}
	
//	@PutMapping("/stock/{id}")
//	public ResponseEntity<String> updateStock(@PathVariable("id") Long id, 
//											  @RequestBody Stock stock){
//		log.debug("updateStock(Long id, Stock stock) - start - params: id: {}, stock: {}", id, stock);
//		stockService.updateStock(id, stock);
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Alterado com sucesso!");
//	}
	
	@PatchMapping("/stock/{id}")
	public ResponseEntity<String> updateQuantityInStock(@PathVariable("id") Long id, @RequestBody Long newQuantity){
		log.info("updateQuantityInStock(Long id, Long newQuantity) - start - params: id: {}, newQuantity: {}", id, newQuantity);
		stockService.updateQuantityInStock(id, newQuantity);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Alterado com sucesso!");
	}
	
	@DeleteMapping("/stock/{id}")
	public ResponseEntity<String> deleteStock(@PathVariable("id") Long id){
		log.info("deleteStock(Long id) - start - params: id: {}", id);
		stockService.deleteStock(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deletado com sucesso!");
	}
	
	
	
	
	
}
