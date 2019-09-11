package com.api.loja.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.loja.exceptions.InvalidAgencyException;
import com.api.loja.exceptions.NotFoundException;
import com.api.loja.exceptions.UnathorizedDeleteException;
import com.api.loja.feing.CEPFeingClient;
import com.api.loja.models.Agencia;
import com.api.loja.models.DTO.AgencyDTO;
import com.api.loja.models.DTO.CepDTO;
import com.api.loja.repository.AgenciaRepository;


@Service
public class AgenciaService {
	
	private static final Logger log = LoggerFactory.getILoggerFactory().getLogger(AgenciaService.class.getName());
	
	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Autowired
	private CEPFeingClient cepFeingClient;
	
	@Autowired
	private StockService stockService;
	
	public List<Agencia> getAllAgencias() {
		return agenciaRepository.findAll();
	}

	public Agencia getOneById(Long id) {
		log.info("getOneById(Long id) - start - params: id {}", id);
		Agencia agencia = agenciaRepository.findById(id).orElseThrow(
				() -> new NotFoundException("Agencia nao encontrada!"));
		return agencia;
	}

	public void createNewAgencia(AgencyDTO newAgenciaDTO) {
		log.info("createNewAgencia(AgencyDTO newAgenciaDTO) - start - params: {}", newAgenciaDTO);
		validaAgenciaDTO(newAgenciaDTO);
		
		Agencia newAgencia = CreateAgencia(newAgenciaDTO);
		log.warn("agenciaRepository - start - method: save");
		agenciaRepository.save(newAgencia);
	}
	

	public void updateAgency(Long id, AgencyDTO newAgenciaDTO) {
		log.info("updateAgency(Long id, AgencyDTO newAgenciaDTO) - start - params: id: {}, newAgenciaDTO: {}", id, newAgenciaDTO);
		validaAgenciaDTO(newAgenciaDTO);
		
		Agencia oldAgencia = new Agencia();
		oldAgencia = getOneById(id);
		
		Agencia newAgencia = CreateAgencia(newAgenciaDTO);
		
		log.warn("Copying Properties - start");
		BeanUtils.copyProperties(newAgencia, oldAgencia, "agenciaId");
		log.warn("agenciaRepository - start - method: save");
		agenciaRepository.save(oldAgencia);
	}


	public void deleteAgency(Long id)  {
		log.info("deleteAgency(Long id) - start - params: {}", id);
		if(!existsAgencyById(id)) {
			log.warn("deleteAgency - notFound - reason: Agencia não encontrada!");
			throw new NotFoundException("Agencia não encontrada!");
		}
		if(stockService.hasAgencyStock(id)) {
			log.warn("deleteAgency - Unathorized - reason: Agencia possui Estoque ativo!");
			throw new UnathorizedDeleteException("Agencia possui Estoque ativo!");
		}
		log.warn("agenciaRepository - start - method: deleteById)");
		agenciaRepository.deleteById(id);
	}

	public Boolean existsAgencyById(Long id) {
		log.info("existsAgencyById(Long id) - start - params: {}", id);
		return agenciaRepository.existsById(id);
	}

	public void validaAgenciaDTO(AgencyDTO newAgenciaDTO) {
		log.info("validaAgenciaDTO(AgencyDTO newAgenciaDTO) - start - params: newAgenciaDTO: {}", newAgenciaDTO);
		
		if(newAgenciaDTO.getCep().isEmpty() || newAgenciaDTO.getCep().equalsIgnoreCase("") || newAgenciaDTO.getCep().length() != 8) {
			log.warn("validaAgenciaDTO - InvalidAgency - reason: Invalid Cep");
			throw new InvalidAgencyException("Invalid Cep");
		}
		
		if (newAgenciaDTO.getName().isEmpty() || newAgenciaDTO.getName().equalsIgnoreCase("")) {
			log.warn("validaAgenciaDTO - InvalidAgency - reason: Invalid name");
			throw new InvalidAgencyException("Invalid name");
		}
		
	}

	private Agencia CreateAgencia(AgencyDTO newAgenciaDTO) {
		log.info("CreateAgencia(AgencyDTO newAgenciaDTO) - start - params: newAgenciaDTO: {}", newAgenciaDTO);
		
		CepDTO cepResponse = new CepDTO();
		log.warn("CepFeingCleint - start - method: getValidaCep(String cep) - params: cep: {}", newAgenciaDTO.getCep());
		cepResponse = cepFeingClient.getValidaCep(newAgenciaDTO.getCep());

		Agencia newAgencia = new Agencia();
		newAgencia.setBairro(cepResponse.getBairro());
		newAgencia.setCep(newAgenciaDTO.getCep());
		newAgencia.setComplemento(cepResponse.getComplemento());
		newAgencia.setLocalidade(cepResponse.getLocalidade());
		newAgencia.setLogradouro(cepResponse.getLogradouro());
		newAgencia.setName(newAgenciaDTO.getName());
		newAgencia.setUf(cepResponse.getUf());

		return newAgencia;
	}
}
