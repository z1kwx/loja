package com.api.loja.models;

import java.util.List;

import lombok.Data;

@Data
public class LojaProducts {

	private Long id;
	private String productName;
	private String productType;
	private Double value;
	private Boolean availability;
	private List<LojaAgency> allAgencys;
	
}
