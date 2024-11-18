package br.com.ambev.util;

import br.com.ambev.entity.Pedido;
import br.com.ambev.repository.PedidoRepository;

public class GeradorCodigoUtil {

	PedidoRepository pedidoRepository;
	
	/* Cria um código do pedido usando o ultimo registro gravado */
	public Long geradorPedido () {
		Long codPedido = 0l;
		Pedido pedido = pedidoRepository.findTopByOrderByCodPedidoDesc();
		if(pedido != null) {
			codPedido = Long.sum(pedido.getCodPedido(),1L);
		}else {
			codPedido = 1l;
		}
		return codPedido;
	}
}
