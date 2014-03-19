package edu.furb.sistemanfe.persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.NotaFiscal;

@PersistenceController
public class NotaFiscalDAO extends JPACrud<NotaFiscal, Long> {

	private static final long serialVersionUID = 651356385536088856L;
	
}
