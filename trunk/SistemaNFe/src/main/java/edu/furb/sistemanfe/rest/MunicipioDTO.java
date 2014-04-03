package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.Municipio;

public class MunicipioDTO {
	private Long id = null;
	private String codigoIbge = null;
	private String nome = null;
	private Estado estado;
	
	public MunicipioDTO(Long id, String codigoIbge, String nome, Estado estado){
		this.codigoIbge =codigoIbge;
		this.nome = nome;
		this.estado = estado;
		this.id = id;
	}
	
	public MunicipioDTO(){		
	}

	public MunicipioDTO(Municipio municipio) {
		super();
		this.codigoIbge = municipio.getCodigoIbge();
		this.nome = municipio.getNome();
		this.estado = municipio.getEstado();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
