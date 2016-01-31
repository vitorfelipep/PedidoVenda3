package com.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.pedidovenda.model.ItemPedido;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.Pedidos;
import com.pedidovenda.util.jpa.Transactional;

public class EstoqueService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public void baixarItensEstoque(Pedido pedido){
		pedido = this.pedidos.porId(pedido.getId());
		
		for(ItemPedido item : pedido.getItemPedido()){
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	public void retornarItensEstoque(Pedido pedido) {
		pedido = pedidos.porId(pedido.getId());
		
		for(ItemPedido item : pedido.getItemPedido()){
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
	}
}
