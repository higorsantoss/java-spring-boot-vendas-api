package br.com.higor.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higor.vendas.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	
	
}
