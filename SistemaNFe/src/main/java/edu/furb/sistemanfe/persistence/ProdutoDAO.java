package edu.furb.sistemanfe.persistence;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.rest.ProdutoDTO;

@PersistenceController
public class ProdutoDAO extends JPACrud<Produto, Long> {

	private static final long serialVersionUID = 1L;

	public List<Produto> pesquisar(ProdutoDTO dto) {
		return null;
	}
	

}
