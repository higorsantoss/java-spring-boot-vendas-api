package br.com.higor.vendas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.higor.vendas.exception.SenhaInvalidaException;
import br.com.higor.vendas.model.Usuario;
import br.com.higor.vendas.repository.UsuarioRepositoty;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepositoty usuarioRepository;

	@Transactional
	public Usuario salvar (Usuario u) {
		return usuarioRepository.save(u);
	}
	
	public UserDetails autenticar(Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getLogin());
		boolean senhaCorreta = encoder.matches(usuario.getSenha(), user.getPassword());
		
		if(senhaCorreta) {
			return user;
		}
		throw new SenhaInvalidaException();
	}
	
	
	//COM JWT 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username)
						.orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado na base de dados."));
		
		
		String [] roles = usuario.isAdmin() ? new String []{ "ADMIN","USER"} : new String [] {"USER"};
		
		 return User
	                .builder()
	                .username(usuario.getLogin())
	                .password(usuario.getSenha())
	                .roles(roles)
	                .build();
	}
	
	
	
	
	
	/*  SEM JWT
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException { if(!username.equals("Tommy")) { throw new
	 * UsernameNotFoundException("Usuario nao encontrado na base."); }
	 * 
	 * return User .builder() .username("Tommy") .password(encoder.encode("456"))
	 * .roles("USER","ADMIN") .build(); }
	 */

}
