package com.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.service.EmissaoPedidoService;
import com.pedidovenda.util.jsf.FacesUtil;

@Named
@RequestScoped
public class EmissaoPedidoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmissaoPedidoService emissaoPedidoService;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	public void emitirPedido(){
		this.pedido.removerItemVazio();
		
		try{
			this.pedido = this.emissaoPedidoService.emitir(this.pedido);
			//lançar um evento CDI.fire
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(pedido));
			
			FacesUtil.addInfoMesage("Pedido Emitido com sucesso!");
		}finally{
			this.pedido.adicionarItemVazio();
		}
	}
}
