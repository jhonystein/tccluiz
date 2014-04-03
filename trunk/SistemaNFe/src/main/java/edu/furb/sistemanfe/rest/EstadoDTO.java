package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.Pais;

public class EstadoDTO {
	private Long id = null;
	private String sigla = null;
	private String nome = null;
	private String codigoIbge = null;
	private Pais pais = null;
		
	public EstadoDTO(){		
	}

	public EstadoDTO(Estado estado) {
		super();
		this.codigoIbge = estado.getCodigoIbge();
		this.nome = estado.getNome();
		this.sigla = estado.getSigla();
		this.pais = estado.getPais();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
}
