package br.com.ambev.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/** DTO com dados de entrada dos produtos vinculados ao pedido */
@Data
@Builder
public class PedidoProdutoInputDTO implements Serializable {

	private static final long serialVersionUID = 1L;	

	@NotNull
	private Long codProduto;

	@NotNull
	private Long quantidadeProduto;

	@NotNull
	private String nomeProduto;

	@NotNull
	private Double valorUnidade;

}
