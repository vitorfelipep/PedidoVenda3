package com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.repository.Clientes;
import com.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter{
	
	private Clientes clientes;
	
	public ClienteConverter() {
		// TODO Auto-generated constructor stub
		this.clientes = (Clientes) CDIServiceLocator.getBean(Clientes.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Cliente retorno = null;
		
		if(value != null){
			retorno = this.clientes.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value != null){
			return ((Cliente) value).getId().toString();
		}
		return "";
	}

}
