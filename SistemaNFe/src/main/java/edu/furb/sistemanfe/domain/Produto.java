package edu.furb.sistemanfe.domain;

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
@Table(name="TBPRODUTO")
@TableGenerator(name="GenProduto", table="TBSEQUENCIAS", pkColumnName="CDSEQUENCIA", pkColumnValue="PRODUTOESSEQ", valueColumnName="VLSEQUENCIA")
public class Produto {

	@Id
	@Column(name="IDPRODUTO")
	@GeneratedValue(generator="GenProduto", strategy=GenerationType.TABLE)
	private Long id;
	
	@Column(name = "DSCODIGO", length = 100)
	private String codigo;
	@Column(name = "NMPRODUTO", length = 100)
	private String nome;
	@ManyToOne
	@JoinColumn(name = "IDEMITENTE")
	private Emitente emitente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((emitente == null) ? 0 : emitente.hashCode());
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (emitente == null) {
			if (other.emitente != null)
				return false;
		} else if (!emitente.equals(other.emitente))
			return false;
		return true;
	}

}
