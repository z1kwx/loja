package com.api.loja.service;

import java.util.List;

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

	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private StockService stockService;
	
	public List<Products> getAllProducts(){
		List<Products> allProducts = productsRepository.findAll();
		return allProducts;	
	}


	public Products getOneProductById(Long id) {
		Products product = productsRepository.findById(id).orElseThrow(
							() -> new NotFoundException("Produto não encontrado!"));
		return product;
	}


	public void postProduct(Products newProduct) {
		validaProduto(newProduct);
		productsRepository.save(newProduct);
	}

	
	public void updateProduct(Long id, Products alteredProduct) {
		Products savedProduct = getOneProductById(id);
		validaProduto(alteredProduct);
		BeanUtils.copyProperties(alteredProduct, savedProduct, "productId");
		productsRepository.save(savedProduct);
	}

	
	public void deleteProduct(Long id) {
		if(!existsProductById(id)) {
			throw new NotFoundException("Produto não encontrado!");
		}
		if(stockService.hasProductStock(id)) {
			throw new UnathorizedDeleteException("Produto cadastrado em Estoque!");
		}
		productsRepository.deleteById(id);					
	}
	
	
	
	
	public void validaProduto(Products product) {
		if(product.getProductName().isEmpty() || product.getProductName().equalsIgnoreCase("")) {
			throw new InvalidProductException("Nome do produto invalido!");
		}
		if(product.getProductType().isEmpty() || product.getProductType().equalsIgnoreCase("")) {
			throw new InvalidProductException("Tipo do produto invalido!");
		}
		if(product.getProductValue() <= 0) {
			throw new InvalidProductException("Valor do produto invalido!");
		}
	}
	
	public Boolean existsProductById(Long id) {
		return productsRepository.existsById(id);
	}
	
}
