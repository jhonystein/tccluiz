package edu.furb.sistemanfe.pojo;

import java.io.Serializable;

public class ProdutoGraficoVendas implements Serializable{

	private static final long serialVersionUID = -6577163576510157286L;
	
	private String dsCodigo;
	private String nmProduto;
	private Double totalItens;
	
	public ProdutoGraficoVendas() {
	}
	
	public ProdutoGraficoVendas(String dsCodigo, String nmProduto,
			Double totalItens) {
		super();
		this.dsCodigo = dsCodigo;
		this.nmProduto = nmProduto;
		this.totalItens = totalItens;
	}

	public String getDsCodigo() {
		return dsCodigo;
	}

	public void setDsCodigo(String dsCodigo) {
		this.dsCodigo = dsCodigo;
	}

	public String getNmProduto() {
		return nmProduto;
	}

	public void setNmProduto(String nmProduto) {
		this.nmProduto = nmProduto;
	}
	
	public Double getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(Double quantidade) {
		this.totalItens = quantidade;
	}
	
	
	
	
	
}
