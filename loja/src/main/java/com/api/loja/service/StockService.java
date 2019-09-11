package com.api.loja.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.loja.exceptions.AlreadyExistsExcpetion;
import com.api.loja.exceptions.InvalidStockQuantity;
import com.api.loja.exceptions.NotFoundException;
import com.api.loja.models.Stock;
import com.api.loja.repository.StockRepository;

@Service
public class StockService {
	
	private static final Logger log = LoggerFactory.getILoggerFactory().getLogger(StockService.class.getName());

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@Autowired
	private ProductsService productService;
	
	public List<Stock> getAllStock() {
		log.info("getAllStock - start");
		return stockRepository.findAll();
	}


	public Stock getOneStockById(Long id) {
		log.info("getOneStockById(Long id) - start - params: id:{}", id);
		Stock stock = stockRepository.findById(id).orElseThrow(
				() -> new NotFoundException("Stock não encontrado!"));
		return stock;
	}


	public List<Stock> getAllStocksByAgenciaId(Long agenciaId) {
		log.info("getAllStocksByAgenciaId(Long agenciaId) - start - params: agenciaId:{}", agenciaId);
		List<Stock> allAgenStocks = new ArrayList<>();
		allAgenStocks = stockRepository.findByAgenciaId(agenciaId);
		return allAgenStocks;
	}


	public List<Stock> getAllStocksByProductId(Long productId) {
		log.info("getAllStocksByProductId(Long productId) - start - params: productId: {}", productId);
		List<Stock> allProductStocks = new ArrayList<>();
		allProductStocks = stockRepository.findByProductId(productId);
		return allProductStocks;
	}


	public Stock getStockByAgenIdAndProcudctId(Long agenciaId, Long productId) {
		//to aqui fazendo log
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
	
	public void updateQuantityInStock(Long id, Long newQuantity) {
		Stock stock =  getOneStockById(id);
		
		if(newQuantity < 0) {
			throw new InvalidStockQuantity("Quantidade para estoque informada é invalida");
		}
		stock.setQuantityInStock(newQuantity);
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
