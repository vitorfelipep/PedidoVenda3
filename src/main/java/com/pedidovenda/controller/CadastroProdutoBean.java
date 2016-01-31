package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.pedidovenda.model.Categoria;
import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.Categorias;
import com.pedidovenda.service.CadastroProdutoService;
import com.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Categorias categorias;

	private Produto produto;

	private Categoria categoriaPai;

	private List<Categoria> categoriasRaizes;
	private List<Categoria> subCategorias;

	@Inject
	private CadastroProdutoService cadastroProdutoService;

	public CadastroProdutoBean() {
		produto = new Produto();
	}

	public void inicializar() {
		System.out.println("Inicializando... lista de categorias...");
		if (FacesUtil.isNotPostBack()) {
			categoriasRaizes = categorias.raizes();
			
			if(categoriaPai != null){
				carregarSubCategorias();
			}
		}
	}

	public void carregarSubCategorias() {
		subCategorias = categorias.listarSubCategoriasDe(categoriaPai);
	}

	private void limparForm() {
		produto = new Produto();
		categoriaPai = null;
		subCategorias = new ArrayList<Categoria>();
	}

	public void cadastrar() {
		this.produto = cadastroProdutoService.salvar(this.produto);
		limparForm();

		FacesUtil.addInfoMesage("produto salvo com sucesso!");
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
		
		if(this.produto != null){
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	public void setCategoriasRaizes(List<Categoria> categoriasRaizes) {
		this.categoriasRaizes = categoriasRaizes;
	}

	@NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Categoria> getSubCategorias() {
		return subCategorias;
	}
	
	public boolean isEditando(){
		return produto.getId() != null;
	}
}
