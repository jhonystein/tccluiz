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
@Table(name = "TBITEMNOTAFISCAL")
@TableGenerator(name = "GenItemNotaFiscal", table = "TBSEQUENCIAS", pkColumnName = "CDSEQUENCIA", pkColumnValue = "ITEMNOTAFISCALSEQ", valueColumnName = "VLSEQUENCIA")
public class ItemNotaFiscal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2271036933097231871L;
	@Id
	@Column(name = "IDITEMNOTAFISCAL")
	@GeneratedValue(generator = "GenItemNotaFiscal", strategy = GenerationType.TABLE)
	private Long id;
	@Column(name = "NRORDEM", length = 3)
	private Integer ordem;
	@Column(name = "NRCFOP", length = 4)
	private String cfop;
	@Column(name = "DSUNIDADE", length = 4)
	private String unidade;
	@Column(name = "VLQUANTIDADE", length = 11)
	private Double quantidade;
	@Column(name = "VLUNITARIO", length = 11)
	private Double valorUnitario;
	@Column(name = "VLTOTAL", length = 11)
	private Double valorTotal;
	@Column(name = "VLTOTALTRIBUTOS", length = 11)
	private Double valorTotalTributos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public String getCfop() {
		return cfop;
	}
	public void setCfop(String cfop) {
		this.cfop = cfop;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Double getValorTotalTributos() {
		return valorTotalTributos;
	}
	public void setValorTotalTributos(Double valorTotalTributos) {
		this.valorTotalTributos = valorTotalTributos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cfop == null) ? 0 : cfop.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		result = prime * result
				+ ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
		result = prime * result
				+ ((valorTotal == null) ? 0 : valorTotal.hashCode());
		result = prime
				* result
				+ ((valorTotalTributos == null) ? 0 : valorTotalTributos
						.hashCode());
		result = prime * result
				+ ((valorUnitario == null) ? 0 : valorUnitario.hashCode());
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
		ItemNotaFiscal other = (ItemNotaFiscal) obj;
		if (cfop == null) {
			if (other.cfop != null)
				return false;
		} else if (!cfop.equals(other.cfop))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ordem == null) {
			if (other.ordem != null)
				return false;
		} else if (!ordem.equals(other.ordem))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		if (valorTotalTributos == null) {
			if (other.valorTotalTributos != null)
				return false;
		} else if (!valorTotalTributos.equals(other.valorTotalTributos))
			return false;
		if (valorUnitario == null) {
			if (other.valorUnitario != null)
				return false;
		} else if (!valorUnitario.equals(other.valorUnitario))
			return false;
		return true;
	}
	
	
}
