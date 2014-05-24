package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Produto;

public class ProdutoDTO {
	private Long id = null;
	private String codigo = null;
	private String nome = null;
	
	public ProdutoDTO(Long id, String codigo, String nome){
		this.codigo =codigo;
		this.nome = nome;
		this.id = id;
	}
	
	public ProdutoDTO(){		
	}

	public ProdutoDTO(Produto produto) {
		super();
		this.codigo = produto.getCodigo();
		this.nome = produto.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}	
}
