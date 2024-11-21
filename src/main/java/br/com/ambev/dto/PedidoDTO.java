package br.com.ambev.dto;

import java.util.Set;

import br.com.ambev.entity.Pedido;
import br.com.ambev.entity.PedidoProduto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoDTO{
	
	private Pedido pedido;
	
	private Set<PedidoProduto> pedidoProduto;
	

}
