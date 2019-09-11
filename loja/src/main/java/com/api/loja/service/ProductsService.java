package com.api.loja.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.loja.exceptions.InvalidProductException;
import com.api.loja.exceptions.NotFoundException;
import com.api.loja.exceptions.UnathorizedDeleteException;
import com.api.loja.models.Products;
import com.api.loja.repository.ProductsRepository;

@Service
public class ProductsService {

	private static final Logger log = LoggerFactory.getILoggerFactory().getLogger(ProductsService.class.getName());
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private StockService stockService;
	
	public List<Products> getAllProducts(){
		log.info("getAllProducts - start");
		List<Products> allProducts = productsRepository.findAll();
		return allProducts;	
	}


	public Products getOneProductById(Long id) {
		log.info("getOneProductById(Long id) - start - params: {}", id);
		Products product = productsRepository.findById(id).orElseThrow(
							() -> new NotFoundException("Produto não encontrado!"));
		return product;
	}


	public void postProduct(Products newProduct) {
		log.info("postProduct(Products newProduct) - start - params: {}", newProduct);
		validaProduto(newProduct);
		productsRepository.save(newProduct);
	}

	
	public void updateProduct(Long id, Products alteredProduct) {
		log.info("updateProduct(Long id, Products alteredProduct) - start - params: id: {}, alteredProduct: {}", id, alteredProduct);
		Products savedProduct = getOneProductById(id);
		
		validaProduto(alteredProduct);
		log.warn("Copying Properties - start");
		BeanUtils.copyProperties(alteredProduct, savedProduct, "productId");
		log.warn("productsRepository - start - method: save");
		productsRepository.save(savedProduct);
	}

	
	public void deleteProduct(Long id) {
		log.info("deleteProduct(Long id) - start - params: {}", id);
		if(!existsProductById(id)) {
			log.warn("deleteProduct - notFound - reason: Produto não encontrado!");
			throw new NotFoundException("Produto não encontrado!");
		}
		if(stockService.hasProductStock(id)) {
			log.warn("deleteProduct - Unathorized - reason: Produto cadastrado em Estoque!");
			throw new UnathorizedDeleteException("Produto cadastrado em Estoque!");
		}
		log.warn("productsRepository - start - method: deleteById");
		productsRepository.deleteById(id);					
	}
	
	
	
	
	public void validaProduto(Products product) {
		log.info("validaProduto(Products product) - start - params: {}", product);
		if(product.getProductName().isEmpty() || product.getProductName().equalsIgnoreCase("")) {
			log.warn("validaProduto - InvalidProduct - reason: Nome do produto invalido!");
			throw new InvalidProductException("Nome do produto invalido!");
		}
		if(product.getProductType().isEmpty() || product.getProductType().equalsIgnoreCase("")) {
			log.warn("validaProduto - InvalidProduct - reason: Tipo do produto invalido!");
			throw new InvalidProductException("Tipo do produto invalido!");
		}
		if(product.getProductValue() <= 0) {
			log.warn("validaProduto - InvalidProduct - reason: Valor do produto invalido!");
			throw new InvalidProductException("Valor do produto invalido!");
		}
	}
	
	public Boolean existsProductById(Long id) {
		log.info("existsProductById(Long id) - start - params: {}", id);
		return productsRepository.existsById(id);
	}
	
}
