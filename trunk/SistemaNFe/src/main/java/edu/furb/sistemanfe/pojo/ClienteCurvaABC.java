package edu.furb.sistemanfe.pojo;

import java.io.Serializable;
import java.math.BigDecimal;


public class ClienteCurvaABC implements Serializable{
	
	private static final long serialVersionUID = 6317308381080182432L;
	private String documento;
	private String nome; 
	private BigDecimal valorUnitario;
	private Double quantidade;
	private Double consumo;
	private Double consumoAcumulado;
	private Double percentualAcumulado;
	private int qualificacao;
	private String classificacao;

	public ClienteCurvaABC() {
		this.documento = "";
		this.nome = "";
		this.valorUnitario = new BigDecimal(0.0);
		this.quantidade = 0.0;
		this.consumo = 0.0;
		this.consumoAcumulado = 0.0;
		this.percentualAcumulado = 0.0;
		this.qualificacao = 0;
		this.classificacao = "";
	}
	
	public ClienteCurvaABC(String documento, String nome, BigDecimal valorUnitario,
			Double quantidade) {
		super();
		this.documento = documento;
		this.nome = nome;
		this.valorUnitario = valorUnitario;
		this.quantidade = quantidade;
		this.consumo = this.valorUnitario.doubleValue() * this.quantidade;
	}

	public ClienteCurvaABC(String documento, String nome, BigDecimal valorUnitario,
			Double quantidade, Double consumo, Double consumoAcumulado,
			Double percentualAcumulado, int qualificacao) {
		super();
		this.documento = documento;
		this.nome = nome;
		this.valorUnitario = valorUnitario;
		this.quantidade = quantidade;
		this.consumo = consumo;
		this.consumoAcumulado = consumoAcumulado;
		this.percentualAcumulado = percentualAcumulado;
		this.qualificacao = qualificacao;
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
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getConsumo() {
		return consumo;
	}
	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}
	public Double getConsumoAcumulado() {
		return consumoAcumulado;
	}
	public void setConsumoAcumulado(Double consumoAcumulado) {
		this.consumoAcumulado = consumoAcumulado;
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
}
