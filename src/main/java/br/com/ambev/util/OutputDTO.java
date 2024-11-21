package br.com.ambev.util;

import java.io.Serializable;

import br.com.ambev.enumerator.StatusProcessamento;
import br.com.ambev.exception.BusinessException;

/**
 * Interface com os dados padrão de retorno dos serviços
 * 
 */
public interface OutputDTO<T extends OutputDTO<T>> extends Serializable{

	StatusProcessamento getCodStatus();
	
	void setCodStatus(StatusProcessamento codStatus);
	
	String getCodRetorno();
	
	void setCodRetorno(String codRetorno);
	
	String getDscMensagem();
	
	void setDscMensagem(String dscMensagem);
	
	/**
	 * Dado do identificador do objeto de retorno, utilizado, entre outros pontos,
	 * para salvar o identificador como chave de log para pesquisa futura.
	 * 
	 * @return identificador do retorno
	 */
	default String getCodIdentificador() {
		return null;
	}
	
	/**
	 * Marca sucesso em um output default
	 * 
	 * @return o próprio DTO
	 */
	@SuppressWarnings("unchecked")
	default T sucesso() {
		setCodStatus(StatusProcessamento.SU);
		setDscMensagem("SUCESSO");
		return (T) this;
	}

	/**
	 * Marca erro em um output default baseado na exceção
	 * 
	 * @param e exceção ocorrida
	 * @return o próprio DTO
	 */
	@SuppressWarnings("unchecked")
	default T erro(BusinessException e) {
		setCodStatus(e.getCodStatus());
		setCodRetorno(e.getCodErro());
		setDscMensagem(e.getMsgErro());
		return (T) this;
	}
}
