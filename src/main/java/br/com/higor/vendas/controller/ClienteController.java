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
import br.com.higor.vendas.repository.ClientesRepository;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {

	@Autowired
	private ClientesRepository clienteRepository;
		
	@GetMapping                                  
	public ResponseEntity<List<Cliente>> obterTodosOsClientes(){
		List<Cliente> cliente = clienteRepository.findAll();
		return ResponseEntity.ok(cliente);
		
	}
	
	
	@GetMapping(value = "{id}")                            
	public Cliente obterClientePorId(@PathVariable Integer id){
		return clienteRepository.findById(id)
								.orElseThrow( 
										() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, " Cliente nao encontrado ")
										);
	}
	
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED) // retorna o status que voce quer que exiba, tem todos os status do Http
	public Cliente salvar(@RequestBody Cliente cliente){
		return clienteRepository.save(cliente);
	}
	
	
	@DeleteMapping(value = "{id}")            
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Integer id){
		clienteRepository.findById(id)
									 .map(  x -> {
										 clienteRepository.delete(x);
										 return x;
									 } ).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Cliente nao encontrado "));
									 
	} 
	
	
	@PutMapping(value = "{id}")   
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody Cliente c){
		
		clienteRepository.findById(id)
		 .map(  x -> {
			 c.setId(x.getId());
			 clienteRepository.save(c);
			 return x;
		 }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Cliente nao encontrado "));
	
	}
}
