package br.com.ambev.exception;

import br.com.ambev.enumerator.MensagemErroEnum;
import lombok.Getter;

public enum PedidoMensagemException implements MensagemErroEnum {
	
	PEDIDO_NAO_ENCONTRADO("O pedido com o código: {0}, não foi encontrado em nossa base.","PED00001"),
	PEDIDO_DUPLICADO("O pedido com o código: {0}, já está salvo em nossa base.","PED00002"),
	
	
	;
	
	@Getter
	private final String msg;
	@Getter
	private final String errorCode;
	
	PedidoMensagemException(String msg,String errorCode){
		this.msg = msg;
		this.errorCode = errorCode;
	}

}
