package edu.furb.sistemanfe.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="TBPAISES")
@TableGenerator(name="GenPais", table="TBSEQUENCIAS", pkColumnName="CDSEQUENCIA", pkColumnValue="PAISESSEQ", valueColumnName="VLSEQUENCIA")
public class Pais implements Serializable {

	private static final long serialVersionUID = -7452858093082896680L;

	@Id
	@Column(name="IDPAIS")
	@GeneratedValue(generator="GenPais", strategy=GenerationType.TABLE)
	private Long id;
	@Column(name="CDBACEN", length=4)
	private String codigoBacen;
	@Column(name="NMPAIS", length=100)
	private String nome;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigoBacen() {
		return codigoBacen;
	}
	
	public void setCodigoBacen(String codigoBacen) {
		this.codigoBacen = codigoBacen;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoBacen == null) ? 0 : codigoBacen.hashCode());
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
		Pais other = (Pais) obj;
		if (codigoBacen == null) {
			if (other.codigoBacen != null)
				return false;
		} else if (!codigoBacen.equals(other.codigoBacen))
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
