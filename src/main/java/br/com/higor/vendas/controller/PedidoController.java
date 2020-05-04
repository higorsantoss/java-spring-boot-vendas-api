package br.com.higor.vendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.higor.vendas.dto.PedidoDTO;
import br.com.higor.vendas.model.Pedido;
import br.com.higor.vendas.service.PedidoService;

@RestController
@RequestMapping(value = "/api/pedidos")
public class PedidoController{
	
	@Autowired
	private PedidoService service;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Integer salvar(@RequestBody PedidoDTO pedidoDTO) {
		Pedido pedido = service.salvar(pedidoDTO);
		return pedido.getId();
	}
	
}
