
package edu.furb.sistemanfe.business;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.persistence.ProdutoDAO;
import edu.furb.sistemanfe.rest.ProdutoDTO;

@BusinessController
public class ProdutoBC extends DelegateCrud<Produto, Long, ProdutoDAO> {
	private static final long serialVersionUID = 1L;
	
	public Produto buscaPorCodigo(String codigo) {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setCodigo(codigo);
		List<Produto> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
}
