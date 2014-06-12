package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Pais;

public class PaisDTO {
	private Long id = null;
	private String codigoBacen = null;
	private String nome = null;
	
	public PaisDTO(Long id, String codigoBacen, String nome){
		this.codigoBacen =codigoBacen;
		this.nome = nome;
		this.id = id;
	}
	
	public PaisDTO(){		
	}

	public PaisDTO(Pais pais) {
		super();
		this.codigoBacen = pais.getCodigoBacen();
		this.nome = pais.getNome();
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

	public String getCodigoBacen() {
		return codigoBacen;
	}

	public void setCodigoBacen(String codigoBacen) {
		this.codigoBacen = codigoBacen;
	}
}
