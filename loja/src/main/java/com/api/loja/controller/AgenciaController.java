package com.api.loja.controller;

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

import com.api.loja.models.Agencia;
import com.api.loja.models.DTO.AgencyDTO;
import com.api.loja.service.AgenciaService;

@RestController
public class AgenciaController {
	
	@Autowired
	private AgenciaService agenciaService;
	
	@GetMapping("/agencia")
	public ResponseEntity<List<Agencia>> getAllAgencias(){
		List<Agencia> listAgencias = agenciaService.getAllAgencias();
		return ResponseEntity.status(HttpStatus.OK).body(listAgencias);
	}

	@GetMapping("/agencia/{id}")
	public ResponseEntity<Agencia> getOneById(@PathVariable("id") Long id){
		Agencia agencia = new Agencia();
		agencia = agenciaService.getOneById(id);
		return ResponseEntity.status(HttpStatus.OK).body(agencia);
	}
	
	@PostMapping("/agencia")
	public ResponseEntity<String> createNewAgencia(@RequestBody AgencyDTO newAgenciaDTO){
		agenciaService.createNewAgencia(newAgenciaDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Criado com suscesso!");
	}

	@PutMapping("/agencia/{id}")
	public ResponseEntity<String> updateAgency(@PathVariable("id") Long id, @RequestBody AgencyDTO newAgenciaDTO){
		agenciaService.updateAgency(id, newAgenciaDTO);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Alterado com sucesso!");
	}
	
	@DeleteMapping("agencia/{id}")
	public ResponseEntity<String> deleteAgency(@PathVariable("id") Long id){
		agenciaService.deleteAgency(id);
		return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
	}
	
}
