package br.com.higor.vendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.higor.vendas.model.Produto;
import br.com.higor.vendas.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	/** SELECT
	 * 
	 * @return
	 */
	
	@GetMapping                                  
	public ResponseEntity<List<Produto>> obterTodosOsProdutos(){
		List<Produto> produto = produtoRepository.findAll();
		return ResponseEntity.ok(produto);
		
	}
	
	/** INSERT
	 * 
	 * @param produto
	 * @return
	 */
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED) // retorna o status que voce quer que exiba, tem todos os status do Http
	public Produto salvar(@RequestBody Produto produto){
		return produtoRepository.save(produto);
	}
	
}
