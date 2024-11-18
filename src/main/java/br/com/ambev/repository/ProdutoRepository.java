package br.com.ambev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ambev.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {

}
