package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Pais;
import edu.furb.sistemanfe.rest.PaisDTO;

@PersistenceController
public class PaisDAO extends JPACrud<Pais, Long> {

	private static final long serialVersionUID = -16372274543317509L;

	public List<Pais> pesquisar(PaisDTO dto) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Pais> query = builder.createQuery(Pais.class);
		Root<Pais> entity = query.from(Pais.class);
		query.select(entity);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (dto.getId() != null) {
			Predicate p = builder.equal(entity.<Long> get("id"),
					dto.getId());
			predicateList.add(p);
		}
		if (dto.getCodigoBacen() != null) {
			Predicate p = builder.equal(entity.<String> get("codigoBacen"),
					dto.getCodigoBacen());
			predicateList.add(p);
		}
		if (dto.getNome() != null) {
			Predicate p = builder.equal(entity.<String> get("nome"),
					dto.getNome());
			predicateList.add(p);
		}		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);	
		return getEntityManager().createQuery(query).getResultList();
	}
}
