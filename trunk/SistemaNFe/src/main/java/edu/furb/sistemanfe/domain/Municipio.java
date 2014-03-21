package edu.furb.sistemanfe.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name="TBMUNICIPIOS")
@TableGenerator(name="GenMunicipio", table="TBSEQUENCIAS", pkColumnName="CDSEQUENCIA", pkColumnValue="MUNICIPIOSSEQ", valueColumnName="VLSEQUENCIA")
public class Municipio implements Serializable {

	private static final long serialVersionUID = 4247577448229507284L;

	@Id
	@Column(name="IDMUNICIPIO")
	@GeneratedValue(generator="GenMunicipio", strategy=GenerationType.TABLE)
	private Long id;
	@Column(name="CDIBGE", length=7)
	private String codigoIbge;
	@Column(name="NMMUNICIPIO", length=100)
	private String nome;
	@ManyToOne
	@JoinColumn(name="IDESTADO")
	private Estado estado;
	
	
	public Municipio() {
	}
	
	public Municipio(String codigoIbge, String nome, Estado estado) {
		super();
		this.codigoIbge = codigoIbge;
		this.nome = nome;
		this.estado = estado;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoIbge == null) ? 0 : codigoIbge.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Municipio other = (Municipio) obj;
		if (codigoIbge == null) {
			if (other.codigoIbge != null)
				return false;
		} else if (!codigoIbge.equals(other.codigoIbge))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
