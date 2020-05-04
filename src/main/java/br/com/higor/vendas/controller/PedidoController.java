package br.com.higor.vendas.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.higor.vendas.dto.InformacaoItemPedidoDTO;
import br.com.higor.vendas.dto.InformacoesPedidoDTO;
import br.com.higor.vendas.dto.PedidoDTO;
import br.com.higor.vendas.model.ItemPedido;
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
	
	@GetMapping("{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service.obterPedidoCompleto(id)
						.map( p -> converter(p))
						.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido Nao Econtrado"));
	}
	
	private InformacoesPedidoDTO converter(Pedido p) {
		return InformacoesPedidoDTO.builder()
							.codigo(p.getId())
							.dataPedido(p.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
							.nomeCliente(p.getCliente().getNome())
							.total(p.getTotal())
							.status(p.getStatus().name())
							.items(converter(p.getItemPedido()))
							.build();
	}
	
	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		if(CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream()
					.map( i -> InformacaoItemPedidoDTO
													.builder()
													.descricaoProduto(i.getProduto().getDescricao())
													.precoUnitario(i.getProduto().getPreco())
													.quantidade(i.getQuantidade())
													.build()

							).collect(Collectors.toList());
	}
}
