
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Empresa;
import edu.furb.sistemanfe.persistence.EmpresaDAO;

@BusinessController
public class EmpresaBC extends DelegateCrud<Empresa, Long, EmpresaDAO> {
	
	private static final long serialVersionUID = 1L;

}
