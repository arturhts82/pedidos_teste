package br.com.ambev.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/** DTO com dados de entrada dos produtos vinculados ao pedido */
@Data
@Builder
public class PedidoProdutoInputDTO implements Serializable {

	private static final long serialVersionUID = 1L;	

	@NonNull 
	private Long codProduto;
	@NonNull 
	private Long quantidadeProduto;
	@NonNull 
	private String nomeProduto;
	@NonNull 
	private BigDecimal valorUnidade;

}
