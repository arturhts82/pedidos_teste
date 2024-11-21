package br.com.ambev.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import br.com.ambev.enumerator.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entidade com dados do pedido*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PEDIDO")
public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="COD_PEDIDO")
	private Long codPedido;
	
	@Column(name="VALOR_TOTAL_PEDIDO")
	private BigDecimal valorTotalPedido;
	
	@Column(name="DATA_PEDIDO")
	private LocalDate dataPedido;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS_PEDIDO")
	private StatusPedido statusPedido;
	
	@Column(name="COD_CLIENTE")
	private Long codCliente;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="pedido")
	private Set<PedidoProduto> pedidoProduto;
}
