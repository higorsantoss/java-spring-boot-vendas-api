package br.com.higor.vendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higor.vendas.model.Usuario;

public interface UsuarioRepositoty extends JpaRepository<Usuario, Integer>{

	Optional<Usuario> findByLogin(String login);
	
}
