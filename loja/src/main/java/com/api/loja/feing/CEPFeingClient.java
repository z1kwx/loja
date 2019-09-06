package com.api.loja.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.loja.models.DTO.CepDTO;

@FeignClient(name = "CEPFeingClient", url = "https://viacep.com.br")
public interface CEPFeingClient {

	@GetMapping("/ws/{cep}/json/")
	public CepDTO getValidaCep(@PathVariable(name = "cep", required = true) String cep);
	
}
