package edu.furb.sistemanfe.view;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ManagementController;
import edu.furb.sistemanfe.business.ProdutoBC;
import edu.furb.sistemanfe.pojo.ProdutoCurvaABC;

@Named
@ManagementController
@RequestScoped
public class CurvaABCProdutoMB implements Serializable {

	private static final long serialVersionUID = -6715166593794694353L;
	@Inject
	private ProdutoBC produtoBC;
	@Inject
	private MessageContext messageContext;
	
	private Date dataIni = null;
	private Date dataFim = null;
	private List<ProdutoCurvaABC> listaDados = null;
	private PieChartModel pieModel1;
	private LineChartModel lineModel1;

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
	
	public CurvaABCProdutoMB() {
	}

	public String consultarRegistros() {
		this.listaDados = produtoBC.getDadosCurvaABC(this.dataIni, this.dataFim);
		return "lista_curva_produto";
	}
	
	public List<ProdutoCurvaABC> getDadosCurvaABC() {
		return this.listaDados;
	}
	
	public String graficoCurvaABC(){
		try{
			listaDados = produtoBC.getDadosCurvaABC(this.dataIni, this.dataFim);
			createPieModel();
			createLineModels();
			return "grafico_curva_abc_produto";
		}catch(Exception ex){
			messageContext
			.add("Falha ao tentar obter dados para o gráfico: {0}", SeverityType.ERROR, ex.getMessage());
			return "";
		}
	}
	
	private void createPieModel() {
        pieModel1 = new PieChartModel();
        Double total = new Double(0.0);
        
        Map<String, Double> listDados = new HashMap<String, Double>();
        
        for (ProdutoCurvaABC object : listaDados) {
        	if(listDados.containsKey(object.getClassificacao())){
        		listDados.put(object.getClassificacao(), listDados.get(object.getClassificacao()) + object.getConsumo() );        		
        	}else{
        		listDados.put(object.getClassificacao(), object.getConsumo());
        	}
        	total += object.getConsumo();
		}
        
        java.text.DecimalFormat df = new java.text.DecimalFormat( "R$ #,##0.00" );
        for (String key : listDados.keySet()) {
        	pieModel1.set(key + " "+ df.format( listDados.get(key) ), listDados.get(key));
		}
        pieModel1.setTitle("Por valor: "+ df.format( total )  );
        pieModel1.setShowDataLabels(true);
        pieModel1.setLegendPosition("w");
    }
	
	private void createLineModels() {
		lineModel1 = initLinearModel();
        lineModel1.setTitle("Pontos por Classificação");
        lineModel1.setLegendPosition("e");
        lineModel1.setShowPointLabels(true);
        lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Classificação"));
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Percentual");
        yAxis.setTickAngle(15);
        yAxis.setMin(0);
        yAxis.setMax(100);
    }
	
	private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Período");
        Map<String, Double> listDados = new HashMap<String, Double>();
        
        for (ProdutoCurvaABC object : listaDados) {
        	if(!listDados.containsKey(object.getClassificacao())){        		
        		listDados.put(object.getClassificacao(), object.getPercentualAcumulado());
        	}
		}
        boys.set("0", 0);
        for (String key : listDados.keySet()) {
        	boys.set(key, listDados.get(key));
		}
        boys.set("100", 100);
 
        model.addSeries(boys);
         
        return model;
    }
	
	public PieChartModel getGraficoCurvaABC() {
		return pieModel1;
	}
	
	public LineChartModel getGraficoLineCurvaABC() {
		return lineModel1;
	}
}
