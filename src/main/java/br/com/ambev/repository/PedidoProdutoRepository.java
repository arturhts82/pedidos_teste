package br.com.ambev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ambev.entity.PedidoProduto;


public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto,Long>{

	List<PedidoProduto> findByCodPedido(Long codPedido);

}
