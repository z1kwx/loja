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
		log.info("getStockByAgenIdAndProcudctId(Long agenciaId, Long productId) - start - params: agenciaId: {}, productId: {}", agenciaId, productId);
		Stock stock = new Stock();
		stock = stockRepository.findByAgenciaIdAndProductId(agenciaId, productId);
		return stock;
	}


	public void createStock(Stock stock) {
		log.info("createStock(Stock stock) - start - params: stocks: {}", stock);
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
		log.info("updateQuantityInStock(Long id, Long newQuantity) - start - params: id: {}, newQuantity: {}", id, newQuantity);
		Stock stock =  getOneStockById(id);
		
		if(newQuantity < 0) {
			log.warn("updateQuantityInStock - InvalidStock - reason: Quantidade para estoque informada é invalida");
			throw new InvalidStockQuantity("Quantidade para estoque informada é invalida");
		}
		stock.setQuantityInStock(newQuantity);
		stockRepository.save(stock);
	}
	
	public void deleteStock(Long id) {
		log.info("deleteStock(Long id) - start - params: id: {}", id);
		if(!stockRepository.existsById(id)) {
			log.warn("deleteStock - NotFound - reason: Estoque nao encontrado!");
			throw new NotFoundException("Estoque nao encontrado!");
		}
		log.warn("stockRepository - start - method: deletebyId");
		stockRepository.deleteById(id);
	}
	

	public void validaStock(Stock stock) {
		log.info("validaStock(Stock stock) - start - params: stock: {}", stock);
		
		if(!productService.existsProductById(stock.getProductId())) {
			log.warn("validaStock - NotFound - reason: Produto não encontrado!");
			throw new NotFoundException("Produto não encontrado!");
		}
		if(!agenciaService.existsAgencyById(stock.getAgenciaId())) {
			log.warn("validaStock - NotFound - reason: Agencia não encontrada!");
			throw new NotFoundException("Agencia não encontrada!");
		}
		if(stock.getQuantityInStock() < 0) {
			log.warn("validaStock - InvalidStock - reason: Quantidade em Stock invalida!");
			throw new InvalidStockQuantity("Quantidade em Stock invalida!");
		}
	}
	
	public void thisProductExistisInThisAgencia(Long productId, Long agenciaId) {
		log.info("thisProductExistisInThisAgencia(Long productId, Long agenciaId) - start - params: productId: {}, agenciaId: {}", productId, agenciaId);
		Boolean exists = false;
		List<Stock> allAgenStocks = getAllStocksByAgenciaId(agenciaId);
		for (Stock obj : allAgenStocks) {
			if(obj.getProductId() == productId) {
				exists = true;
			}
		}
		if(exists) {
			log.warn("thisProductExistisInThisAgencia - AlreadyExists - reson: This product already exists in this agency!");
			throw new AlreadyExistsExcpetion("This product already exists in this agency!");
		}
	}


	public Boolean hasAgencyStock(Long agenciaId) {
		log.info("hasAgencyStock(Long agenciaId) - start - params: agenciaId: {}", agenciaId);
		List<Stock> agenciaStock = getAllStocksByAgenciaId(agenciaId);
		if(agenciaStock.size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}


	public Boolean hasProductStock(Long productId) {
		log.info("hasProductStock(Long productId) - start - params: productId: {}", productId);
		List<Stock> productStocks = getAllStocksByProductId(productId);
		if(productStocks.size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}



}
