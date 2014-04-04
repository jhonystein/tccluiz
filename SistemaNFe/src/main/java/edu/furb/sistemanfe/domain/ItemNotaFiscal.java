package edu.furb.sistemanfe.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "TBITEMNOTAFISCAL")
@TableGenerator(name = "GenItemNotaFiscal", table = "TBSEQUENCIAS", pkColumnName = "CDSEQUENCIA", pkColumnValue = "ITEMNOTAFISCALSEQ", valueColumnName = "VLSEQUENCIA")
public class ItemNotaFiscal implements Serializable {

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
	@Column(name = "DSUNIDADE", length = 6)
	private String unidade;
	@Column(name = "VLQUANTIDADE", length = 11)
	private Double quantidade;
	@Column(name = "VLUNITARIO", length = 13)
	private BigDecimal valorUnitario;
	@Column(name = "VLTOTAL", length = 13)
	private BigDecimal valorTotal;
	@Column(name = "VLTOTALTRIBUTOS", length = 13)
	private BigDecimal valorTotalTributos;
	@ManyToOne
	@JoinColumn(name = "IDNOTAFISCAL_ID")
	private NotaFiscal notafiscal;
	@Embedded
	private Produto produto = null;
	
	public ItemNotaFiscal(){
		
	}

	public ItemNotaFiscal(Integer ordem, String cfop, String unidade,
			Double quantidade, BigDecimal valorUnitario, BigDecimal valorTotal,
			BigDecimal valorTotalTributos, NotaFiscal notafiscal,
			Produto produto) {
		super();
		this.ordem = ordem;
		this.cfop = cfop;
		this.unidade = unidade;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.valorTotal = valorTotal;
		this.valorTotalTributos = valorTotalTributos;
		this.notafiscal = notafiscal;
		this.produto = produto;
		
	}

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

	

}
