package com.api.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.loja.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{

	List<Stock> findByAgenciaId(Long agenciaId);
	
	List<Stock> findByProductId(Long productId);
	
	Stock findByAgenciaIdAndProductId(Long agenciaId, Long productId);
}
