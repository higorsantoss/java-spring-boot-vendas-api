package br.com.higor.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higor.vendas.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
