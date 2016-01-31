package com.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.Pedidos;
import com.pedidovenda.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public Pedido salvar(Pedido pedido){
		
		if(pedido.isNovo()){
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}
		
		pedido.recalcularValorTotal();
		
		if(pedido.isNaoAlteravel()){
			throw new NegocioException("Pedido não pode ser alterado nos status"
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		if(pedido.getItemPedido().isEmpty()){
			throw new NegocioException("O pedido deve possuir pelo menos 1 item!");
		}
		
		if(pedido.isValorTotalNegativo()){
			throw new NegocioException("Valor total do pedido não pode ser negativo!");
		}
		
		
		pedido = this.pedidos.guardar(pedido);
		return pedido;
	}

}
