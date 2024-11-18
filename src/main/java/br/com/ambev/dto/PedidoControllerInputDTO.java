package br.com.ambev.dto;

import java.io.Serializable;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoControllerInputDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private final Long codCliente;
	
	@NotNull
	private Set<PedidoProdutoInputDTO> listaPedidoProduto;
	

}
