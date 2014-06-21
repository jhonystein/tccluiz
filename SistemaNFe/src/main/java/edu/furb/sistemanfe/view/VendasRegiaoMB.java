package edu.furb.sistemanfe.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

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
	private BarChartModel graficoBarraVendas;
	
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
	
	public String graficoVendas(){
		listaDados = notaFiscalBC.getRegiaoVendas(this.dataIni, this.dataFim);
		createBarModel();
		return "grafico_vendas_regiao";
	}
	
	public BarChartModel getGraficoBarraVendas() {
		return graficoBarraVendas;
	}
	
	private void createBarModel() {
		BigDecimal maiorValor = new BigDecimal(0);
		BigDecimal menorValor = new BigDecimal(0);
		for (RegiaoVendas objeto : this.listaDados) {
			if(objeto.getValor().compareTo(maiorValor)==1){
				maiorValor = objeto.getValor();
			}
			if(objeto.getValor().compareTo(menorValor)==-1){
				menorValor = objeto.getValor();
			}
		}
		
		maiorValor = maiorValor.add(maiorValor.multiply(new BigDecimal(0.10)));
		
		graficoBarraVendas = initBarModel();

		graficoBarraVendas.setTitle("Vendas por município");
		graficoBarraVendas.setLegendPosition("ne");

		Axis xAxis = graficoBarraVendas.getAxis(AxisType.X);
		xAxis.setLabel("Municípios");

		Axis yAxis = graficoBarraVendas.getAxis(AxisType.Y);
		yAxis.setLabel("Valores");
		yAxis.setMin(menorValor);
		yAxis.setMax(maiorValor);
	}
	
	private BarChartModel initBarModel() {
	
		BarChartModel model = new BarChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Municípios");
		
		for (RegiaoVendas objeto : this.listaDados) {
			boys.set(objeto.getMunicipio().getNome(), objeto.getValor());
		}		

		model.addSeries(boys);

		return model;
	}

}
