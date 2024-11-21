package br.com.ambev.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/** DTO com dados de saida dos produtos vinculados ao pedido */
@Data
@Builder
public class PedidoProdutoOutputDTO implements Serializable {

	private static final long serialVersionUID = 1L;	

	private Long codProduto;

	private String nomeProduto;

	private BigDecimal valorUnidade;

	private Long quantidadeProduto;

}
