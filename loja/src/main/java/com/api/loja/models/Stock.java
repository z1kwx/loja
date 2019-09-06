package com.api.loja.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "stock")
public class Stock {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "agencia_id")
	private Long agenciaId;
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "quantity_in_stock")
	private Long quantityInStock;
	
}
