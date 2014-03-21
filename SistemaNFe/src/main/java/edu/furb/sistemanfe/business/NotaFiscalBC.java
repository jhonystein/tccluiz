
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.persistence.NotaFiscalDAO;

@BusinessController
public class NotaFiscalBC extends DelegateCrud<NotaFiscal, Long, NotaFiscalDAO> {
	private static final long serialVersionUID = 1L;
	
	
}
