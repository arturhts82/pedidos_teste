package br.com.ambev.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class PedidoControllerInputDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NonNull 
	private Long codCliente;

	private Set<PedidoProdutoInputDTO> listaPedidoProduto;
	

}
