package br.com.higor.vendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.higor.vendas.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Query(" select p from Pedido p left join fetch p.itemPedido where p.id = :id ")
	Optional<Pedido> findByIdFetchItemPedido(@Param("id") Integer id);
	
}
