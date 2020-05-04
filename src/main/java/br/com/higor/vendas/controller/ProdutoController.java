package br.com.higor.vendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.higor.vendas.model.Cliente;
import br.com.higor.vendas.model.Produto;
import br.com.higor.vendas.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping                                  
	public ResponseEntity<List<Produto>> obterTodosOsProdutos(){
		List<Produto> produto = produtoRepository.findAll();
		return ResponseEntity.ok(produto);
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED) // retorna o status que voce quer que exiba, tem todos os status do Http
	public Produto salvar(@RequestBody Produto produto){
		return produtoRepository.save(produto);
	}
	
	@DeleteMapping(value = "{id}")            
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Integer id){
		produtoRepository.findById(id)
									 .map(  x -> {
										 produtoRepository.delete(x);
										 return x;
									 } ).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Produto nao encontrado "));
									 
	} 
	
	@PutMapping(value = "{id}")   
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody Produto p){
		
		produtoRepository.findById(id)
		 .map(  x -> {
			 p.setId(x.getId());
			 produtoRepository.save(p);
			 return x;
		 }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Produto nao encontrado "));
	
	}
}
