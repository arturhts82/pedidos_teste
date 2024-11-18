package br.com.ambev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ambev.entity.Pedido;
import br.com.ambev.enumerator.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

	Optional<Pedido> findByCodClienteAndStatusPedidoOrderByDataPedidoDesc(Long codCliente,
			StatusPedido criado);

	Pedido findTopByOrderByCodPedidoDesc();

}
