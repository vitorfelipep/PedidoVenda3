package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.Pedidos;
import com.pedidovenda.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;

	private PedidoFilter filtro;
	private List<Pedido> pedidosFiltrados;

	public PesquisaPedidosBean() {

		this.filtro = new PedidoFilter();
		this.pedidosFiltrados = new ArrayList<Pedido>();

	}

	public void pesquisar() {
		pedidosFiltrados = pedidos.filtrados(filtro);
	}
	
	public StatusPedido[] getStatus(){
		return StatusPedido.values();
	}
	
	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}

}
