
package edu.furb.sistemanfe.business;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.persistence.EmitenteDAO;
import edu.furb.sistemanfe.rest.EmitenteDTO;

@BusinessController
public class EmitenteBC extends DelegateCrud<Emitente, Long, EmitenteDAO> {
	private static final long serialVersionUID = 1L;
	
	public Emitente buscaDocumento(String documento) {
		EmitenteDTO dto = new EmitenteDTO();
		dto.setDocumento(documento);
		List<Emitente> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
}
