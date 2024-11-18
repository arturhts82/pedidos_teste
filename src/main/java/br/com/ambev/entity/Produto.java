package br.com.ambev.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/** Entidade com os dados do produto */
@Data
@Builder
@Entity
@Table(name = "PRODUTO")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "COD_PRODUTO")
	private Long codProduto;

	@Column(name = "NOME_PRODUTO")
	private String nomeProduto;

	@Column(name = "VALOR_UNIDADE")
	private Double valorUnidade;

}
