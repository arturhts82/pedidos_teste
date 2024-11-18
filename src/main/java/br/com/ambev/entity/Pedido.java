package br.com.ambev.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.ambev.enumerator.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/** Entidade com dados do pedido*/
@Data
@Builder
@Entity
@Table(name="PEDIDO")
public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="COD_PEDIDO")
	private Long codPedido;
	
	@Column(name="VALOR_TOTAL_PEDIDO")
	private Double valorTotalPedido;
	
	@Column(name="DATA_PEDIDO")
	private LocalDate dataPedido;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS_PEDIDO")
	private StatusPedido statusPedido;
	
	@Column(name="COD_CLIENTE")
	private Long codCliente;

	@OneToMany
	@JoinColumn(name = "COD_PEDIDO", nullable = false, insertable=false, updatable=false)
	private Set<PedidoProduto> pedidoProduto;
}
