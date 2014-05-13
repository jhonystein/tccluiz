package edu.furb.sistemanfe.persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Pais;

@PersistenceController
public class PaisDAO extends JPACrud<Pais, Long> {
	private static final long serialVersionUID = 1445087087029883431L;
}
