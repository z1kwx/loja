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
		allLojaProducts = createLojaProduct();
		return allLojaProducts;
	}

	
	public List<LojaProducts> createLojaProduct(){
		List<LojaProducts> allLojaProducts = new ArrayList<>();
		List<Products> allProducts = new ArrayList<>();
		
		allProducts = productsService.getAllProducts();
		
		for (Products obj : allProducts) {
			LojaProducts newLojaProduct = new LojaProducts();
			List<LojaAgency> allLojaAgencys = new ArrayList<>();
			
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
			
			
			for (Long agenIds : allAgencysId) {
				Agencia agen = new Agencia();
				agen = agenciaService.getOneById(agenIds);
				
				LojaAgency newLojaAgency = new LojaAgency();
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
