package edu.furb.sistemanfe.rest;

import java.util.Date;

import edu.furb.sistemanfe.domain.ArquivoXML;

public class ArquivoXMLDTO {
	private Long id = null;
	private String nome = null;
	private Date dataUpload = null;
	private String status = null;
	
	public ArquivoXMLDTO(Long id, String nome, String status){
		this.id = id;
		this.nome = nome;
		this.status = status;
	}
	
	public ArquivoXMLDTO(){		
	}

	public ArquivoXMLDTO(ArquivoXML arquivoXML) {
		super();
		this.id = arquivoXML.getId();
		this.nome = arquivoXML.getNome();
		this.status = arquivoXML.getStatus();
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

	public Date getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
