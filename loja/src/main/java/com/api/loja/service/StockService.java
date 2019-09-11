package com.api.loja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.loja.exceptions.AlreadyExistsExcpetion;
import com.api.loja.exceptions.InvalidStockQuantity;
import com.api.loja.exceptions.NotFoundException;
import com.api.loja.models.Stock;
import com.api.loja.models.DTO.StockQuantityDTO;
import com.api.loja.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@Autowired
	private ProductsService productService;
	
	public List<Stock> getAllStock() {
		return stockRepository.findAll();
	}


	public Stock getOneStockById(Long id) {
		Stock stock = stockRepository.findById(id).orElseThrow(
				() -> new NotFoundException("Stock não encontrado!"));
		return stock;
	}


	public List<Stock> getAllStocksByAgenciaId(Long agenciaId) {
		List<Stock> allAgenStocks = new ArrayList<>();
		allAgenStocks = stockRepository.findByAgenciaId(agenciaId);
		return allAgenStocks;
	}


	public List<Stock> getAllStocksByProductId(Long productId) {
		List<Stock> allProductStocks = new ArrayList<>();
		allProductStocks = stockRepository.findByProductId(productId);
		return allProductStocks;
	}


	public Stock getStockByAgenIdAndProcudctId(Long agenciaId, Long productId) {
		Stock stock = new Stock();
		stock = stockRepository.findByAgenciaIdAndProductId(agenciaId, productId);
		return stock;
	}


	public void createStock(Stock stock) {
		validaStock(stock);
		thisProductExistisInThisAgencia(stock.getProductId(), stock.getAgenciaId());
		stockRepository.save(stock);
	}
	
//	public void updateStock(Long id, Stock newStock) {
//		Stock oldStock = getOneStockById(id);
//		validaStock(newStock);
//		thisProductExistisInThisAgencia(newStock.getProductId(), newStock.getAgenciaId());
//		BeanUtils.copyProperties(newStock, oldStock, "id");
//		stockRepository.save(oldStock);
//	}
	
	public void updateQuantityInStock(Long id, StockQuantityDTO newQuantity) {
		Stock stock =  getOneStockById(id);
		
		if(newQuantity.getQuantity() < 0) {
			throw new InvalidStockQuantity("Quantidade para estoque informada é invalida");
		}
		stock.setQuantityInStock(newQuantity.getQuantity());
		stockRepository.save(stock);
	}
	
	public void deleteStock(Long id) {
		if(!stockRepository.existsById(id)) {
			throw new NotFoundException("Estoque nao encontrado!");
		}
		stockRepository.deleteById(id);
	}
	

	public void validaStock(Stock stock) {
		if(!productService.existsProductById(stock.getProductId())) {
			throw new NotFoundException("Produto não encontrado!");
		}
		if(!agenciaService.existsAgencyById(stock.getAgenciaId())) {
			throw new NotFoundException("Agencia não encontrada!");
		}
		if(stock.getQuantityInStock() < 0) {
			throw new InvalidStockQuantity("Quantidade em Stock invalida!");
		}
	}
	
	public void thisProductExistisInThisAgencia(Long productId, Long agenciaId) {
		Boolean exists = false;
		List<Stock> allAgenStocks = getAllStocksByAgenciaId(agenciaId);
		for (Stock obj : allAgenStocks) {
			if(obj.getProductId() == productId) {
				exists = true;
			}
		}
		if(exists) {
			throw new AlreadyExistsExcpetion("This product already exists in this agency!");
		}
	}


	public Boolean hasAgencyStock(Long agenciaId) {
		List<Stock> agenciaStock = getAllStocksByAgenciaId(agenciaId);
		if(agenciaStock.size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}


	public Boolean hasProductStock(Long productId) {
		List<Stock> productStocks = getAllStocksByProductId(productId);
		if(productStocks.size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}



}
