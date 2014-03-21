
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.persistence.EstadoDAO;

@BusinessController
public class EstadoBC extends DelegateCrud<Estado, Long, EstadoDAO> {
	private static final long serialVersionUID = 1L;
	
	
}
