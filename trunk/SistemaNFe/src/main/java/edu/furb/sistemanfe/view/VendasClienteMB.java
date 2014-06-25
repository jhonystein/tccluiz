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
import org.primefaces.model.chart.HorizontalBarChartModel;

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
	private HorizontalBarChartModel graficoBarraVendas;
	
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
	public String graficoVendas(){
		listaDados = clienteBC.getClientesVendas(this.dataIni, this.dataFim);
		createBarModel();
		return "grafico_vendas_cliente";
	}
	
	public BarChartModel getGraficoBarraVendas() {
		return graficoBarraVendas;
	}
	
	private void createBarModel() {
		
		BigDecimal maiorValor = new BigDecimal(0);
		BigDecimal menorValor = new BigDecimal(0);
		/*
		 * Obtendos os dados de limite superior e inferior do gráfico;
		 */
		for (ClienteVendas objeto : this.listaDados) {
			if(objeto.getValor().compareTo(maiorValor)==1){
				maiorValor = objeto.getValor();
			}
			if(objeto.getValor().compareTo(menorValor)==-1){
				menorValor = objeto.getValor();
			}
		}
		
		graficoBarraVendas = initBarModel();

		graficoBarraVendas.setTitle("Vendas por clientes");
		graficoBarraVendas.setLegendPosition("ne");

		Axis xAxis = graficoBarraVendas.getAxis(AxisType.X);
		xAxis.setLabel("Valores");
		xAxis.setTickAngle(35);


		Axis yAxis = graficoBarraVendas.getAxis(AxisType.Y);
		yAxis.setLabel("Clientes");
		yAxis.setMin(menorValor);
		yAxis.setMax(maiorValor);
	}
	
	private HorizontalBarChartModel initBarModel() {
	
		HorizontalBarChartModel model = new HorizontalBarChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Clientes");
		/**
		 * Carregando os dados no gráfico
		 */
		for (ClienteVendas objeto : this.listaDados) {
			boys.set(objeto.getCliente().getNome(), objeto.getValor());
		}		

		model.addSeries(boys);

		return model;
	}

}
