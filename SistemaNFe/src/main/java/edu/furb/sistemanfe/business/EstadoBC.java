package edu.furb.sistemanfe.business;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.Pais;
import edu.furb.sistemanfe.persistence.EstadoDAO;

@BusinessController
public class EstadoBC  extends DelegateCrud<Estado, Long, EstadoDAO> {
	private static final long serialVersionUID = 2765212508437044287L;

	@Inject
	private PaisBC paisBC;
	
	@Startup
	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			paisBC.load();
			Pais pais = paisBC.findAll().get(0);
			insert(new Estado("SC", "Santa Catarina", "123", pais));
			insert(new Estado("PR", "Paraná", "321", pais));
		}
	}
}
