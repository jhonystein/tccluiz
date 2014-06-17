package edu.furb.sistemanfe.view.chart;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import edu.furb.sistemanfe.business.ProdutoBC;

public class CharCurvaABCProduto implements Serializable{

	private static final long serialVersionUID = 1347660924998976873L;

	@Inject 
	private ProdutoBC produtoBC;
	
	
	@PostConstruct
	public void init() {
		//createBarModels();
	}
	
}
