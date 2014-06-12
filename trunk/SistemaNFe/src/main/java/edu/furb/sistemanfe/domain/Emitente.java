package edu.furb.sistemanfe.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="TBEMITENTES")
@TableGenerator(name="GenEmitente", table="TBSEQUENCIAS", pkColumnName="CDSEQUENCIA", pkColumnValue="EMITENTESSEQ", valueColumnName="VLSEQUENCIA")
public class Emitente implements Serializable {

	private static final long serialVersionUID = -2811351774276705991L;

	@Id
	@Column(name="IDEMITENTE")
	@GeneratedValue(generator="GenEmitente", strategy=GenerationType.TABLE)
	private Long id;
	@Column(name="NRDOCUMENTO", length=20)
	private String documento;
	@Column(name="NMEMITENTE", length=100)
	private String nome;
	@Column(name="NRINSCESTADUAL", length=20)
	private String inscricaoEstadual;
	@Embedded
	private Endereco endereco;
	
	public Emitente(){
		this.endereco = new Endereco();
	}
	
	public Emitente(String documento, String nome, String inscricaoEstadual,
			Endereco endereco) {
		super();
		this.endereco = new Endereco();
		this.documento = documento;
		this.nome = nome;
		this.inscricaoEstadual = inscricaoEstadual;
		this.endereco = endereco;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inscricaoEstadual == null) ? 0 : inscricaoEstadual.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Emitente other = (Emitente) obj;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inscricaoEstadual == null) {
			if (other.inscricaoEstadual != null)
				return false;
		} else if (!inscricaoEstadual.equals(other.inscricaoEstadual))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
