package br.com.higor.vendas.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override //Parte de Autenticacao
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication() // Autenticacao em Memoria em tempo de execucao
			.passwordEncoder(passwordEncoder())
			.withUser("Higor")
			.password(passwordEncoder().encode("123"))
			.roles("USER");
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
			
			.and()
			.formLogin();
	}

	
}
