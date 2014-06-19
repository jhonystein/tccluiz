package edu.furb.sistemanfe.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.stereotype.ManagementController;
import edu.furb.sistemanfe.business.ProdutoBC;
import edu.furb.sistemanfe.pojo.ProdutoVendas;


@Named
@ManagementController
@RequestScoped
public class VendasProdutoMB implements Serializable {

	private static final long serialVersionUID = -5176381063764671148L;

	@Inject
	private ProdutoBC produtoBC;
	
	private Date dataIni = null;
	private Date dataFim = null;
	private List<ProdutoVendas> listaDados = null;

	public Date getDataIni() {
		return dataIni;
	}
	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public VendasProdutoMB() {
	}
	
	public String consultarRegistros() {
		listaDados = produtoBC.getProdutosVendas(this.dataIni, this.dataFim);
		return "lista_vendas_produto";
	}
	
	public List<ProdutoVendas> getListaDados() {
		return listaDados;
	}

}
