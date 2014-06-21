package edu.furb.sistemanfe.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.stereotype.ManagementController;
import edu.furb.sistemanfe.business.ClienteBC;
import edu.furb.sistemanfe.pojo.ClienteCurvaABC;

@Named
@ManagementController
@RequestScoped
public class CurvaABCClienteMB implements Serializable {

	private static final long serialVersionUID = 4858443088139789324L;

	@Inject
	private ClienteBC clienteBC;
	
	private Date dataIni = null;
	private Date dataFim = null;
	private List<ClienteCurvaABC> listaDados = null;

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
	
	public CurvaABCClienteMB() {
	}

	public String consultarRegistros() {
		this.listaDados = clienteBC.getDadosCurvaABC(this.dataIni, this.dataFim);
		return "lista_curva_cliente";
	}
	
	public List<ClienteCurvaABC> getDadosCurvaABC() {
		return this.listaDados;
	}
}
