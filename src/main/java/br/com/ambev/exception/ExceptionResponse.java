package br.com.ambev.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/* Classe responsável pela geração das mensagens de exceção customizaveis*/
@ControllerAdvice
public class ExceptionResponse {
	
	@ExceptionHandler(value= {BusinessException.class})
	protected ResponseEntity<?> handleException(BusinessException ex,WebRequest webRequest){
		Map<String,Object> body = new HashMap<>();
		body.put("codErro", ex.getCodErro());
		body.put("msgErro",ex.getMessage());
		body.put("hora",LocalDateTime.now());
		
		return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
	}

}
