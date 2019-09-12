package com.api.loja.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.loja.models.LojaProducts;
import com.api.loja.service.LojaService;

@RestController
public class LojaController {

	@Autowired
	private LojaService lojaService;
	
	@GetMapping("/loja")
	public ResponseEntity<List<LojaProducts>> listAllProducts(){
		List<LojaProducts> allProducts = new ArrayList<>();
		allProducts = lojaService.listAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(allProducts);
	}
	
}
