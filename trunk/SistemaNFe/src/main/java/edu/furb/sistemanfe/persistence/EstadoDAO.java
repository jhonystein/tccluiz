package edu.furb.sistemanfe.persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Estado;

@PersistenceController
public class EstadoDAO extends JPACrud<Estado, Long> {
	
	private static final long serialVersionUID = 1L;

}
