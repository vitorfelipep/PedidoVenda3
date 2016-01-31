package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.util.jsf.FacesUtil;
import com.pedidovenda.util.mail.Mailer;

@Named
@RequestScoped
public class EnvioEmailPedidoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void enviarPedido(){
		MailMessage messager = mailer.novaMensagem();
		messager.to(this.pedido.getCliente().getEmail())
			.subject("Seu pedido de nº "+ this.pedido.getId() + " em suas mãos!")
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
			.put("pedido", this.pedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
		
		FacesUtil.addInfoMesage("Pedido enviado por e-mail  com sucesso!");
	}
	
}
