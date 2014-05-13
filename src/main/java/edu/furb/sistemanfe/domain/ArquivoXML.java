package edu.furb.sistemanfe.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TBARQUIVOXML")
@TableGenerator(name="GenArquivoXML", table="TBSEQUENCIAS", pkColumnName="CDSEQUENCIA", pkColumnValue="ARQUIVOXMLSEQ", valueColumnName="VLSEQUENCIA")
public class ArquivoXML implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="IDARQUIVOXML")
	@GeneratedValue(generator="GenArquivoXML", strategy=GenerationType.TABLE)
	private Long id;
	@Column(name="ARQUIVO")
	@Lob
    private byte[] arquivo;
	@Column(name="NMARQUIVO", length=100)
	private String nome;
	@Column(name="DTDATAUPLOAD")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataUpload;
	//TODO:Estudar a possibilidade de criar um emun
	@Column(name="FLGSTATUS", length=1)
	private String status;
//	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
//	@JoinColumn(name = "IDEMPRESA")
//	private Empresa empresa;
	
	public ArquivoXML() {
		
	}
	
	public ArquivoXML(Empresa empresa, String nome, byte[] arquivo) {
//		this.empresa = empresa;
		this.nome = nome;
		this.arquivo = arquivo;
		this.status = "N";
	}
	
	public ArquivoXML(Empresa empresa, String nome, byte[] arquivo, String status) {
//		this.empresa = empresa;
		this.nome = nome;
		this.arquivo = arquivo;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	
	
	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
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

//	public Empresa getEmpresa() {
//		return empresa;
//	}
//
//	public void setEmpresa(Empresa empresa) {
//		this.empresa = empresa;
//	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((dataUpload == null) ? 0 : dataUpload.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
//		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoXML other = (ArquivoXML) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (dataUpload == null) {
			if (other.dataUpload != null)
				return false;
		} else if (!dataUpload.equals(other.dataUpload))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
//		if (empresa == null) {
//			if (other.empresa != null)
//				return false;
//		} else if (!empresa.equals(other.empresa))
//			return false;
		return true;
	}
	
}
