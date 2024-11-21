package br.com.ambev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ambev.entity.Pedido;
import br.com.ambev.enumerator.StatusPedido;


public interface PedidoRepository extends JpaRepository<Pedido,Long> {

	Optional<Pedido> findByCodClienteAndStatusPedidoOrderByDataPedidoDesc(Long codCliente,
			StatusPedido status);

	Pedido findTopByOrderByCodPedidoDesc();

}
