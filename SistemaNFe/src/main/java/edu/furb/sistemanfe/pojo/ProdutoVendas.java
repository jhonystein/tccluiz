package edu.furb.sistemanfe.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import edu.furb.sistemanfe.domain.Produto;

public class ProdutoVendas implements Serializable{

	private static final long serialVersionUID = -870555729103429783L;
	
	private Produto produto;
	private Double quantidade;
	private BigDecimal valor;
	
	public ProdutoVendas() {
	}

	public ProdutoVendas(Produto produto, Double quantidade, BigDecimal valor) {
		super();
		this.produto = produto;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}	
}
