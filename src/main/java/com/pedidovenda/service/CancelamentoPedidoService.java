package com.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.Pedidos;
import com.pedidovenda.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido cancelar(Pedido pedido) {
		pedido = pedidos.porId(pedido.getId());
		
		if(pedido.isNaoCancelavel()){
			throw new NegocioException("Pedido não pode ser cancelado no status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		if(pedido.isEmitido()){
			this.estoqueService.retornarItensEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = pedidos.guardar(pedido);
		
		return pedido;
	}
 
}
