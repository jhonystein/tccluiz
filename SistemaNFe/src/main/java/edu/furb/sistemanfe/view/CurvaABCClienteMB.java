package edu.furb.sistemanfe.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
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
	@Inject
	private MessageContext messageContext;
	
	private Date dataIni = null;
	private Date dataFim = null;
	private List<ClienteCurvaABC> listaDados = null;
	private PieChartModel pieModel1;

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
	
	public String graficoCurvaABC(){
		try{
			listaDados = clienteBC.getDadosCurvaABC(this.dataIni, this.dataFim);
			createPieModel();
			return "grafico_curva_abc_cliente";
		}catch(Exception ex){
			messageContext
			.add("Falha ao tentar obter dados para o gráfico: {0}", SeverityType.ERROR, ex.getMessage());
			return "";
		}
	}
	
	private void createPieModel() {
        pieModel1 = new PieChartModel();
        BigDecimal total = new BigDecimal(0);
        
        Map<String, BigDecimal> listDados = new HashMap<String, BigDecimal>();
        
        for (ClienteCurvaABC object : listaDados) {
        	if(listDados.containsKey(object.getClassificacao())){
        		listDados.put(object.getClassificacao(), listDados.get(object.getClassificacao()).add(object.getValorTotal()) );        		
        	}else{
        		listDados.put(object.getClassificacao(), object.getValorTotal());
        	}
        	total = total.add(object.getValorTotal());
		}
        
        java.text.DecimalFormat df = new java.text.DecimalFormat( "R$ #,##0.00" );
        for (String key : listDados.keySet()) {
        	pieModel1.set(key + " "+ df.format( listDados.get(key) ), listDados.get(key));
		}
        
        pieModel1.setTitle("Por valor: "+ df.format( total )  );
        pieModel1.setShowDataLabels(true);
        pieModel1.setLegendPosition("w");
    }
	
	public PieChartModel getGraficoCurvaABC() {
		return pieModel1;
	}
}
