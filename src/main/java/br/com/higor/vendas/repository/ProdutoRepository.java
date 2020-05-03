package br.com.higor.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higor.vendas.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
