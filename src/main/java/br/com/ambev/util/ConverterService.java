package br.com.ambev.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Serviço de conversão de dados para teste de serviços
 * 
 */
@Service
public class ConverterService {
	
	@Autowired
	private ObjectMapper objectMapper;
	public static final String EMPTY = "";
	
	/**
	 * Converte Object em String JSON
	 */
	public String converterParaJson (Object dto) {
		String registro = null;
		if(dto == null) {
			return EMPTY;
		} 
		try {
			registro = objectMapper.writeValueAsString(dto);
		}catch(JsonProcessingException e) {
			//log.warn(Erro ao converter DTO: "+ dto.toString(), e);
			registro = dto.toString();
		}
		return registro;
	}
	

}
