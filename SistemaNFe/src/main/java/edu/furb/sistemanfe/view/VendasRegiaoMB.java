package edu.furb.sistemanfe.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.stereotype.ManagementController;
import edu.furb.sistemanfe.business.NotaFiscalBC;
import edu.furb.sistemanfe.pojo.RegiaoVendas;


@Named
@ManagementController
@RequestScoped
public class VendasRegiaoMB implements Serializable {

	private static final long serialVersionUID = -5176381063764671148L;

	@Inject
	private NotaFiscalBC notaFiscalBC;
	
	private Date dataIni = null;
	private Date dataFim = null;
	private List<RegiaoVendas> listaDados = null;

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
	
	public VendasRegiaoMB() {
	}
	
	public String consultarRegistros() {
		listaDados = notaFiscalBC.getRegiaoVendas(this.dataIni, this.dataFim);
		return "lista_vendas_regiao";
	}
	
	public List<RegiaoVendas> getListaDados() {
		return listaDados;
	}

}
