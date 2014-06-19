package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Endereco;

public class ClienteDTO implements BaseDTO {
	
	private Long id = null;
	private String documento = null;
	private String nome = null;
	private String inscricaoEstadual = null;
	private String fone = null;
	private Emitente emitente = null;
	private String email = null;
	private Endereco endereco = null;
	
	public ClienteDTO(){		
	}

	public ClienteDTO(Cliente cliente) {
		super();
		this.documento = cliente.getDocumento();
		this.nome = cliente.getNome();
		this.inscricaoEstadual = cliente.getInscricaoEstadual();
		this.fone = cliente.getFone();
		this.emitente = cliente.getEmitente();
		this.email = cliente.getEmail();
		this.endereco = cliente.getEndereco();
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

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
