package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.Produtos;
import com.pedidovenda.repository.filter.ProdutoFilter;
import com.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;
	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;
	private Produto produtoSelecionado;

	public PesquisaProdutosBean() {
		this.filtro = new ProdutoFilter();
	}

	public void pesquisar() {
		produtosFiltrados = produtos.filtrados(filtro);
	}

	public void excluir() {
		produtos.remover(produtoSelecionado);
		produtosFiltrados.remove(produtoSelecionado);

		FacesUtil.addInfoMesage("Produto " + produtoSelecionado.getSku()
				+ " exclúido com sucesso!");
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
		System.out.println(produtoSelecionado);
	}

}
