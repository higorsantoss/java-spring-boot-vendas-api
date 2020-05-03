package br.com.higor.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.higor.vendas.model.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Integer>{

	//Busca pelo Nome ( like ) utilisando palavras chaves do Spring Data 
	public List<Cliente> findByNomeContaining (String nome);
	
	//@Query(value = "select * from Cliente c where c.nome like %:nome% ", nativeQuery= true)    // usando query nativa 
	//Busca por nome mas agora usando @Query HQL // utilisando a consulta que quiser e com o nome do metodo que quiser
	@Query(value = "select c from Cliente c where c.nome like %:nome% ")
	public List<Cliente> buscandoComLikeNome (@Param("nome") String nome);
	
	@Query(value= " select c from Cliente c left join fetch c.pedidos where c.id = :id ")
	public Cliente obterClienteFetchPedido(@Param("id") Integer id);
	
}
