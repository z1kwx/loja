package com.api.loja.models;

import lombok.Data;

@Data
public class LojaAgency {

	private Long id;
	private String name;
	private String cep;
	private String uf;
	private String localidade;
	
	
}
