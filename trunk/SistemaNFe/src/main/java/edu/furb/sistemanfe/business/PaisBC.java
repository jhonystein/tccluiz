package edu.furb.sistemanfe.business;

import java.util.List;

import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.Pais;
import edu.furb.sistemanfe.persistence.PaisDAO;
import edu.furb.sistemanfe.rest.PaisDTO;

@BusinessController
public class PaisBC  extends DelegateCrud<Pais, Long, PaisDAO> {

	private static final long serialVersionUID = 6750567180949694272L;

	@Startup
	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			insert(new Pais("1058", "Brasil"));
		}
	}
	
	public Pais buscaCodigoBacen(String codigoBacen) {
		PaisDTO dto = new PaisDTO();
		dto.setCodigoBacen(codigoBacen);
		List<Pais> lst = getDelegate().pesquisar(dto);
		if ((lst == null) || (lst.size() == 0)) {
			return null;
		}
		return lst.get(0);
	}
}
