package com.api.loja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.loja.models.Agencia;
import com.api.loja.models.LojaAgency;
import com.api.loja.models.LojaProducts;
import com.api.loja.models.Products;
import com.api.loja.models.Stock;

@Service
public class LojaService {
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@Autowired
	private StockService stockService;

	public List<LojaProducts> listAllProducts() {
		List<LojaProducts> allLojaProducts = new ArrayList<>();
		List<LojaAgency> allLojaAgencys = new ArrayList<>();
		List<Products> allProducts = new ArrayList<>();
		
		LojaProducts newLojaProduct = new LojaProducts();
		LojaAgency newLojaAgency = new LojaAgency();
		
		allProducts = productsService.getAllProducts();
		
		for (Products obj : allProducts) {
			newLojaProduct.setId(obj.getProductId());
			newLojaProduct.setProductName(obj.getProductName());
			newLojaProduct.setProductType(obj.getProductType());
			newLojaProduct.setValue(obj.getProductValue());
			
			List<Stock> allStocksByProductId = stockService.getAllStocksByProductId(obj.getProductId());
			List<Long> allAgencysId = new ArrayList<>();
			
			for (Stock stockObj : allStocksByProductId) {
				if(stockObj.getQuantityInStock() > 0) {
					allAgencysId.add(stockObj.getAgenciaId());
				}
			}
			
			Agencia agen = new Agencia();
			
			for (Long agenIds : allAgencysId) {
				agen = agenciaService.getOneById(agenIds);
				newLojaAgency.setCep(agen.getCep());
				newLojaAgency.setId(agen.getAgenciaId());
				newLojaAgency.setLocalidade(agen.getLocalidade());
				newLojaAgency.setName(agen.getName());
				newLojaAgency.setUf(agen.getUf());
				
				allLojaAgencys.add(newLojaAgency);
			}
			
			if(allLojaAgencys.size() > 0) {
				newLojaProduct.setAvailability(true);
			}else {
				newLojaProduct.setAvailability(false);
			}
			
			newLojaProduct.setAllAgencys(allLojaAgencys);
			
			allLojaProducts.add(newLojaProduct);
		}
		
		
		return allLojaProducts;
	}

	
	
	
}
