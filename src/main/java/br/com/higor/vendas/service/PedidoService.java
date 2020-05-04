package br.com.higor.vendas.service;

import java.util.Optional;

import br.com.higor.vendas.dto.PedidoDTO;
import br.com.higor.vendas.model.Pedido;

public interface PedidoService {

	public Pedido salvar(PedidoDTO pedidoDTO);

	public Optional<Pedido> obterPedidoCompleto (Integer id);
}
