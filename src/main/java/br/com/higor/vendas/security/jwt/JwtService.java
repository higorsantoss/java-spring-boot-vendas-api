package br.com.higor.vendas.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import br.com.higor.vendas.VendasApplication;
import br.com.higor.vendas.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;
	
	@Value("${security.jwt.chaveAssinatura}")
	private String chaveAssinatura;
	
	//Claims - informacoes do token
	private Claims obterClaims(String token) throws ExpiredJwtException{
		return Jwts
					.parser()
					.setSigningKey(chaveAssinatura)
					.parseClaimsJws(token)
					.getBody();
	}
	
	public boolean tokenValido(String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(data);
		} catch (Exception e) {
			return false;
		}
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException{
		return (String) obterClaims(token).getSubject();
	}
	
	//Gerando Token
	public String gerarToken(Usuario user) {
		long expString = Long.valueOf(expiracao); 
		LocalDateTime dataHotaExpiracao = LocalDateTime.now().plusMinutes(expString);
		Instant instant = dataHotaExpiracao.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);
		
		//HashMap<String , Object> claims = new HashMap<>();
		//claims.put("email", "higorsantoss@gmail.com");
		//claims.put("roles", "admin");
		
		return Jwts
					.builder()
					.setSubject(user.getLogin())
					.setExpiration(data)
					//.setClaims(claims)
					.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
					.compact();
	}

	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(VendasApplication.class);
		JwtService service = ctx.getBean(JwtService.class);
		Usuario u = Usuario.builder().login("Higor").build();
		String token = service.gerarToken(u);
		System.out.println(token);
		
		boolean isTokenValido = service.tokenValido(token);
		System.out.println("O Token esta valido ? " + isTokenValido);
		
		System.out.println(service.obterLoginUsuario(token));
		
	}
	
	
}
