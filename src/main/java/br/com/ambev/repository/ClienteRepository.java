package br.com.ambev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ambev.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{

}
