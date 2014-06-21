package edu.furb.sistemanfe.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.furb.sistemanfe.business.ClienteBC;
import edu.furb.sistemanfe.pojo.ClienteVendas;

import br.gov.frameworkdemoiselle.stereotype.ManagementController;

@Named
@ManagementController
@RequestScoped
public class VendasClienteMB implements Serializable {

	private static final long serialVersionUID = -7051142694100439527L;

	@Inject
	private ClienteBC clienteBC;
	
	private Date dataIni = null;
	private Date dataFim = null;
	private List<ClienteVendas> listaDados = null;

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
	
	public VendasClienteMB() {
	}
	
	public String consultarRegistros() {
		listaDados = clienteBC.getClientesVendas(this.dataIni, this.dataFim);
		return "lista_vendas_cliente";
	}
	
	public List<ClienteVendas> getListaDados() {
		return listaDados;
	}

}
