package br.com.ambev.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/** Entidade coom os dados do cliente */
@Data
@Builder
@Entity
@Table(name = "CLIENTE")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COD_CLIENTE")
	private Long codCliente;

	@Column(name = "NOME_CLIENTE")
	private String nomeCliente;

}
