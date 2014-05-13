package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.Pais;
import edu.furb.sistemanfe.persistence.PaisDAO;

@BusinessController
public class PaisBC  extends DelegateCrud<Pais, Long, PaisDAO> {

	private static final long serialVersionUID = 6750567180949694272L;

	@Startup
	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			insert(new Pais("55", "Brasil"));
		}
	}
}
