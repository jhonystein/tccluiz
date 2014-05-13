
package edu.furb.sistemanfe.business;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.persistence.ClienteDAO;
import edu.furb.sistemanfe.rest.ClienteDTO;

@BusinessController
public class ClienteBC extends DelegateCrud<Cliente, Long, ClienteDAO> {
	private static final long serialVersionUID = 1L;	

	public Cliente buscaPorDocumento(String documento) {
		ClienteDTO dto = new ClienteDTO();
		dto.setDocumento(documento);
		List<Cliente> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
	
	
}
