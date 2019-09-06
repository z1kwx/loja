package com.api.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.loja.models.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

	
	
}
