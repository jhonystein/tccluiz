
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.persistence.EmitenteDAO;

@BusinessController
public class EmitenteBC extends DelegateCrud<Emitente, Long, EmitenteDAO> {
	private static final long serialVersionUID = 1L;
	
	
}
