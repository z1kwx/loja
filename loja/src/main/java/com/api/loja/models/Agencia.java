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
@Table(name = "agencia")
public class Agencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agencia_id")
	private Long agenciaId;
	@Column(name = "name")
	private String name;
	@Column(name = "cep")
	private String cep;
	@Column(name = "complemento")
	private String complemento;
	@Column(name = "uf")
	private String uf;
	@Column(name = "localidade")
	private String localidade;
	@Column(name = "bairro")
	private String bairro;
	@Column(name = "logradouro")
	private String logradouro;
}
