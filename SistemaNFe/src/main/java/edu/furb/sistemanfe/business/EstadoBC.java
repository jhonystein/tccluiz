
package edu.furb.sistemanfe.business;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.persistence.EstadoDAO;
import edu.furb.sistemanfe.rest.EstadoDTO;

@BusinessController
public class EstadoBC extends DelegateCrud<Estado, Long, EstadoDAO> {
	private static final long serialVersionUID = 1L;
	
	public Estado buscaSigla(String sigla) {
		EstadoDTO dto = new EstadoDTO();
		dto.setCodigoIbge(sigla);
		List<Estado> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
}
