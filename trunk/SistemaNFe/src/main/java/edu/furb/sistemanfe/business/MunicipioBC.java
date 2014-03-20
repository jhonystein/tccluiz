
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Municipio;
import edu.furb.sistemanfe.persistence.MunicipioDAO;

// To remove unused imports press: Ctrl+Shift+o

@BusinessController
public class MunicipioBC extends DelegateCrud<Municipio, Long, MunicipioDAO> {
	private static final long serialVersionUID = 1L;
	
	
}
