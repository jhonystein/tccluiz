package edu.furb.sistemanfe.pojo;

import java.io.Serializable;
import java.math.BigDecimal;


public class ClienteCurvaABC implements Serializable{
	
	private static final long serialVersionUID = 6317308381080182432L;
	private String documento;
	private String nome; 
	private long quantidade;
	private Double percentualAcumulado;
	private int qualificacao;
	private String classificacao;
	private BigDecimal valorTotal;
	private BigDecimal valortTotalAcumulado;
	

	public ClienteCurvaABC() {
		super();
		this.documento = "";
		this.nome = "";
		this.quantidade = 0;
		this.percentualAcumulado = 0.0;
		this.qualificacao = 0;
		this.classificacao = "";
		this.valorTotal = new BigDecimal(0.0);
		this.valortTotalAcumulado = new BigDecimal(0.0);
	}
	
	public ClienteCurvaABC(String documento, String nome, BigDecimal valorTotal
			, long quantidade) {
		this();
		this.documento = documento;
		this.nome = nome;
		this.valorTotal = valorTotal;
		this.quantidade = quantidade;
		}
	
	public ClienteCurvaABC(String documento, String nome, BigDecimal valorTotal
			) {
		this();
		this.documento = documento;
		this.nome = nome;
		this.valorTotal = valorTotal;
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
	public long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPercentualAcumulado() {
		return percentualAcumulado;
	}
	public void setPercentualAcumulado(Double percentualAcumulado) {
		this.percentualAcumulado = percentualAcumulado;
	}
	public int getQualificacao() {
		return qualificacao;
	}
	public void setQualificacao(int qualificacao) {
		this.qualificacao = qualificacao;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValortTotalAcumulado() {
		return valortTotalAcumulado;
	}

	public void setValortTotalAcumulado(BigDecimal valortTotalAcumulado) {
		this.valortTotalAcumulado = valortTotalAcumulado;
	}
	
}
