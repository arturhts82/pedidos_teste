package br.com.ambev.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/** Entidade com dados dos produtos vinculados ao pedido*/
@Data
@Builder
@Entity
@Table(name="PEDIDO_PRODUTO")
public class PedidoProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PedidoProduto.PK pk;

	@Column(name = "NOME_PRODUTO")
	private String nomeProduto;

	@Column(name = "VALOR_UNIDADE")
	private Double valorUnidade;
	
	@Column(name = "QUANTIDADE_PRODUTO")
	private Long quantidadeProduto;
	
	@Column(name = "VALOR_LOTE")
	private Double valorLote;
	
	@Data
	@Builder
	public static class PK implements Serializable{
	
		private static final long serialVersionUID = 1L;
		
		@Column(name="COD_PEDIDO", nullable=false)
		private Long codPedido;
		
		@Column(name="COD_PRODUTO",nullable=false)
		private Long codProduto;		
		
	}
	
	
}
