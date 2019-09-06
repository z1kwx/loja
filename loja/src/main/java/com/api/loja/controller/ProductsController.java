package com.api.loja.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.loja.models.Products;
import com.api.loja.service.ProductsService;

@RestController
public class ProductsController {
	
	@Autowired
	private ProductsService productsService;
	

	@GetMapping("/products")
	public ResponseEntity<List<Products>> getAllProducts(){
		List<Products> allProducts = new ArrayList<>();
		allProducts = productsService.getAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(allProducts);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Products> getOneProductById(@PathVariable("id") Long id){
		Products product = new Products();
		product = productsService.getOneProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	
	@PostMapping("/products")
	public ResponseEntity<String> postProduct(@RequestBody Products newProduct){
		productsService.postProduct(newProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso!");
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable("id") Long id,
												@RequestBody Products alteredProduct){
		productsService.updateProduct(id, alteredProduct);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Alterado com sucesso!");
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
		productsService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body("Produto Deletado!");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
