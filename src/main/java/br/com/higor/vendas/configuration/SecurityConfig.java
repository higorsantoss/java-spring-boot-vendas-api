package br.com.higor.vendas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.higor.vendas.security.jwt.JwtAuthFilter;
import br.com.higor.vendas.security.jwt.JwtService;
import br.com.higor.vendas.service.impl.UsuarioServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
	}
	
	//AuthenticationManagerBuilder faz a autenticacao do usuario e coloca dentro do contexto do Spring Security ( com o token JWT sera criada a  classe JwtAuthFilter)
	@Override //Parte de Autenticacao   
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* METODO ANTIGO COM AUTENTICACAO BASIC SPRING SECURITY EM TEMPO DE EXECUCAO
		 *  
		 * auth.inMemoryAuthentication() // Autenticacao em Memoria em tempo de execucao
		 * .passwordEncoder(passwordEncoder()) .withUser("Higor")
		 * .password(passwordEncoder().encode("123")) .roles("USER");
		 */
		
		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
		
	}

	@Override  //Parte de Autorizacao
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/api/clientes/**") //URL da api que sera afetada
				.hasAnyRole("USER", "ADMIN") // Varias roles juntas      //pode ser tambem .authenticated() ou .permiteAll() que todo mundo consegue acessar a URL
			.antMatchers("/api/pedidos/**")
				.hasAnyRole("ADMIN")
			.antMatchers("/api/produtos/**")
				.hasRole("USER")
			.antMatchers(HttpMethod.POST, "/api/usuarios/**") // posso passar tambem o metodo http que quero autenticacao e para quem, neste caso esta para todo mundo
				.permitAll()
			.anyRequest().authenticated()
			.and()
			//.formLogin(); pra usar um formulario default
			//.httpBasic(); para utilisar com sessoes 
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // por token, nao tem mais sessao
			.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	
}
