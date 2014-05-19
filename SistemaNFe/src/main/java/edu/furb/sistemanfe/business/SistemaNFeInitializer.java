package edu.furb.sistemanfe.business;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.Priority;
import br.gov.frameworkdemoiselle.lifecycle.Startup;

public class SistemaNFeInitializer {
	
	@Inject
	private EstadoBC estadoBC;
	@Inject
	private MunicipioBC municipioBC;
	
	@Startup
    @Priority(1)
    public void initialize() {  

		try{
			estadoBC.InicializaListaEstados();
			municipioBC.InicializaListaMunicipios();
		}catch(RuntimeException re){
			System.out.println("Falha ao inicializar dados básicos");
			re.printStackTrace();
		}
    }
}
