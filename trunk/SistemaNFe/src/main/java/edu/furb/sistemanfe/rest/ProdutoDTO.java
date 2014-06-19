package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Produto;

public class ProdutoDTO implements BaseDTO {
	private Long id = null;
	private String codigo = null;
	private String nome = null;
	private Emitente emitente = null;
	
	public ProdutoDTO(){		
	}

	public ProdutoDTO(Produto produto) {
		super();
		this.id = produto.getId();
		this.codigo = produto.getCodigo();
		this.nome = produto.getNome();
		this.emitente = produto.getEmitente();
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

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}
}
