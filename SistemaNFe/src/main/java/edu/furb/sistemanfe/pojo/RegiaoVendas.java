package edu.furb.sistemanfe.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import edu.furb.sistemanfe.domain.Municipio;

public class RegiaoVendas implements Serializable {
	private static final long serialVersionUID = 1647669754903257191L;

	private Municipio municipio;
	private Long quantidade;
	private BigDecimal valor;

	public RegiaoVendas() {
	}

	public RegiaoVendas(Municipio municipio, Long quantidade, BigDecimal valor) {
		super();
		this.municipio = municipio;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public String getSiglaUf(){
		return (this.municipio.getEstado()==null)?(""):(this.municipio.getEstado().getSigla());
	}
}
