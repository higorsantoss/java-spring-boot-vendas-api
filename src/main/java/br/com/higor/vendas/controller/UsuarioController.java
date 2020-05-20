package br.com.higor.vendas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.higor.vendas.dto.CredenciaisDTO;
import br.com.higor.vendas.dto.TokenDTO;
import br.com.higor.vendas.exception.SenhaInvalidaException;
import br.com.higor.vendas.model.Usuario;
import br.com.higor.vendas.security.jwt.JwtService;
import br.com.higor.vendas.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioServiceImpl usuarioService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	@PostMapping
	@ResponseBody
	public Usuario salvar(@RequestBody Usuario user) {
		String senhaCriptografada = passwordEncoder.encode(user.getSenha());
		user.setSenha(senhaCriptografada);
		return usuarioService.salvar(user); 
	}

	@PostMapping("/auth")
	@ResponseBody
	public TokenDTO autenticar (@RequestBody CredenciaisDTO credenciais) {
		try {
			Usuario usuario = Usuario.builder().login(credenciais.getLogin()).senha(credenciais.getSenha()).build();
			UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
			String token = jwtService.gerarToken(usuario);
			return new TokenDTO(usuario.getLogin(), token);
		} catch (UsernameNotFoundException | SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		} 
	}
	
	
}
