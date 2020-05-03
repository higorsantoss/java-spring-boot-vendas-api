package br.com.higor.vendas.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.higor.vendas.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	
	
}
