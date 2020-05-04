package br.com.higor.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.higor.vendas.dto.ItemPedidoDTO;
import br.com.higor.vendas.dto.PedidoDTO;
import br.com.higor.vendas.enums.StatusPedido;
import br.com.higor.vendas.exception.RegraNegocioException;
import br.com.higor.vendas.model.Cliente;
import br.com.higor.vendas.model.ItemPedido;
import br.com.higor.vendas.model.Pedido;
import br.com.higor.vendas.model.Produto;
import br.com.higor.vendas.repository.ClientesRepository;
import br.com.higor.vendas.repository.ItemPedidoRepository;
import br.com.higor.vendas.repository.PedidoRepository;
import br.com.higor.vendas.repository.ProdutoRepository;
import br.com.higor.vendas.service.PedidoService;
import lombok.AllArgsConstructor;

@Service
public class PedidoServiceImpl  implements PedidoService{

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClientesRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository; 
	
	@Autowired
	private ItemPedidoRepository itemsPedidosRepository;
	
	@Override
	@Transactional
	public Pedido salvar(PedidoDTO pedidoDTO) {
		Integer idCliente = pedidoDTO.getCliente();
		Cliente cliente = clienteRepository
											.findById(idCliente)
											.orElseThrow(() -> new RegraNegocioException("Codigo de Cliente Invalido!"));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(pedidoDTO.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		
		List<ItemPedido> itemsPedidos = converterItems(pedido, pedidoDTO.getItems());
		pedidoRepository.save(pedido);
		itemsPedidosRepository.saveAll(itemsPedidos);
		pedido.setItemPedido(itemsPedidos);
		return pedido;
	}
	
	private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
		if(items.isEmpty()) {
			throw new RegraNegocioException("Nao e possivel realizar um pedido sem items.");
		}
		return items.stream()
					.map(dto ->{
						
						Integer idProduto = dto.getProduto();
						Produto produto = produtoRepository
															.findById(idProduto)
															.orElseThrow( () -> new RegraNegocioException("Codigo de Produto Invalido: " + idProduto));
						
						ItemPedido itemPedido = new ItemPedido();
						itemPedido.setQuantidade(dto.getQuantidade());
						itemPedido.setPedido(pedido);
						itemPedido.setProduto(produto);
						return itemPedido;
					}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return pedidoRepository.findByIdFetchItemPedido(id);
	}
}
