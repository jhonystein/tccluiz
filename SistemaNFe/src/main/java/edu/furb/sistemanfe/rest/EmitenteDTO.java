package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Usuario;


public class EmitenteDTO {
	private Long id= null;
	private String documento= null;
	private String nome= null;
	private String inscricaoEstadual = null;
	private Usuario usuario = null;
	
	public EmitenteDTO(){		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
