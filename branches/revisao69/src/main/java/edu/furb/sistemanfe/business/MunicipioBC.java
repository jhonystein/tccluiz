
package edu.furb.sistemanfe.business;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Municipio;
import edu.furb.sistemanfe.persistence.MunicipioDAO;
import edu.furb.sistemanfe.rest.MunicipioDTO;

@BusinessController
public class MunicipioBC extends DelegateCrud<Municipio, Long, MunicipioDAO> {
	private static final long serialVersionUID = 1L;
	
	public Municipio buscaCodigoIbge(String codigoIbge) {
		MunicipioDTO dto = new MunicipioDTO();
		dto.setCodigoIbge(codigoIbge);
		List<Municipio> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
}
