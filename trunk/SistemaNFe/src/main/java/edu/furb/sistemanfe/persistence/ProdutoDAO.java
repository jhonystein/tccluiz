package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.rest.ProdutoDTO;

@PersistenceController
public class ProdutoDAO extends JPACrud<Produto, Long> {

	private static final long serialVersionUID = 1L;

	public List<Produto> pesquisar(ProdutoDTO dto) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> objeto = query.from(Produto.class);
		query.select(objeto);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (dto.getId() != null) {
			Predicate p = builder.equal(objeto.<Long> get("id"),
					dto.getId());
			predicateList.add(p);
		}
		if (dto.getCodigo() != null) {
			Predicate p = builder.equal(objeto.<String> get("codigo"),
					dto.getCodigo());
			predicateList.add(p);
		}
		if (dto.getNome() != null) {
			Predicate p = builder.equal(objeto.<String> get("nome"),
					dto.getNome());
			predicateList.add(p);
		}
		//TODO: mimplementar os demais campos;
//		if (dto.getEmitente() == null) {
			//TODO: Deve criticar se naum tem emitente;
//			Predicate p = builder.equal(municipio.<String> get("nome"),
//					municipioDTO.getNome());
//			predicateList.add(p);
//		}
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);	
		return getEntityManager().createQuery(query).getResultList();

	}
	

}
