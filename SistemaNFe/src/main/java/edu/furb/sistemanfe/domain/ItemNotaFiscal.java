package edu.furb.sistemanfe.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Embeddable
public class ItemNotaFiscal {

	@Column(name = "NRORDEM", length = 3)
	private Integer ordem;
	@Column(name = "NRCFOP", length = 4)
	private String cfop;
	@Column(name = "DSUNIDADE", length = 6)
	private String unidade;
	@Column(name = "VLQUANTIDADE", length = 11)
	private String quantidade;
	@Column(name = "VLUNITARIO", length = 13)
	private BigDecimal valorUnitario;
	@Column(name = "VLTOTAL", length = 13)
	private BigDecimal valorTotal;
	@Column(name = "VLTOTALTRIBUTOS", length = 13)
	private BigDecimal valorTotalTributos;	
	@ManyToOne
	@JoinColumn(name = "IDNOTAFISCAL")
	private NotaFiscal notafiscal;
	@ManyToOne
	@JoinColumn(name = "IDPRODUTO")
	private Produto produto;
	
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
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public BigDecimal getValorTotalTributos() {
		return valorTotalTributos;
	}
	public void setValorTotalTributos(BigDecimal valorTotalTributos) {
		this.valorTotalTributos = valorTotalTributos;
	}
	public NotaFiscal getNotafiscal() {
		return notafiscal;
	}
	public void setNotafiscal(NotaFiscal notafiscal) {
		this.notafiscal = notafiscal;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cfop == null) ? 0 : cfop.hashCode());
		result = prime * result
				+ ((notafiscal == null) ? 0 : notafiscal.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		if (notafiscal == null) {
			if (other.notafiscal != null)
				return false;
		} else if (!notafiscal.equals(other.notafiscal))
			return false;
		if (ordem == null) {
			if (other.ordem != null)
				return false;
		} else if (!ordem.equals(other.ordem))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
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
