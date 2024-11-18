package br.com.ambev.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.ambev.enumerator.StatusPedido;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoControllerOutputDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long codPedido;
	
	private Double valorPedido;
	
	private LocalDate dataPedido;

	private StatusPedido statusPedido;

	private Set<PedidoProdutoOutputDTO> listaProdutos;
	

}
