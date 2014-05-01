
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.ArquivoXML;
import edu.furb.sistemanfe.persistence.ArquivoXMLDAO;

@BusinessController
public class ArquivoXMLBC extends DelegateCrud<ArquivoXML, Long, ArquivoXMLDAO> {
	private static final long serialVersionUID = 1L;

}
