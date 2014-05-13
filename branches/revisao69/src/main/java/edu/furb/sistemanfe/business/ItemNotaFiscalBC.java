
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.ItemNotaFiscal;
import edu.furb.sistemanfe.persistence.ItemNotaFiscalDAO;

@BusinessController
public class ItemNotaFiscalBC extends DelegateCrud<ItemNotaFiscal, Long, ItemNotaFiscalDAO> {
	private static final long serialVersionUID = 1L;
	
	
}
