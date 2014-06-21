package edu.furb.sistemanfe.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import edu.furb.sistemanfe.domain.Cliente;

public class ClienteVendas implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1654101589880986050L;
	private Cliente cliente;
	private Long quantidade;
	private BigDecimal valor;
	
	public ClienteVendas() {
	}

	public ClienteVendas(Cliente cliente, Long quantidade, BigDecimal valor) {
		super();
		this.cliente = cliente;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
}
