package br.com.ambev.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entidade com dados dos produtos vinculados ao pedido*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PEDIDO_PRODUTO")
public class PedidoProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "NOME_PRODUTO")
	private String nomeProduto;

	@Column(name = "VALOR_UNIDADE")
	private BigDecimal valorUnidade;
	
	@Column(name = "QUANTIDADE_PRODUTO")
	private Long quantidadeProduto;
	
	@Column(name = "VALOR_LOTE")
	private BigDecimal valorLote;
	
	@Column(name="COD_PRODUTO")
	private Long codProduto;	
	
	@Column(name="COD_PEDIDO")
	private Long codPedido;		

	@JoinColumn(name= "COD_PEDIDO", referencedColumnName = "COD_PEDIDO", updatable = false, insertable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Pedido pedido;

}
