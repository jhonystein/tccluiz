package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.rest.EstadoDTO;

@PersistenceController
public class EstadoDAO extends JPACrud<Estado, Long> {

	private static final long serialVersionUID = 1L;
	
	public List<Estado> pesquisar(EstadoDTO estadoDTO) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Estado> query = builder.createQuery(Estado.class);
		Root<Estado> mestado = query.from(Estado.class);
		query.select(mestado);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (estadoDTO.getId() != null) {
			Predicate p = builder.equal(mestado.<Long> get("id"),
					estadoDTO.getId());
			predicateList.add(p);
		}
		if (estadoDTO.getCodigoIbge() != null) {
			Predicate p = builder.equal(mestado.<String> get("codigoIbge"),
					estadoDTO.getCodigoIbge());
			predicateList.add(p);
		}
		if (estadoDTO.getNome() != null) {
			Predicate p = builder.equal(mestado.<String> get("nome"),
					estadoDTO.getNome());
			predicateList.add(p);
		}

		if (estadoDTO.getSigla() != null) {
			Predicate p = builder.equal(mestado.<String> get("sigla"),
					estadoDTO.getSigla());
			predicateList.add(p);
		}
		//TODO: mimplementar os demais campos;
		if (estadoDTO.getPais() != null) {
//			Predicate p = builder.equal(municipio.<String> get("nome"),
//					municipioDTO.getNome());
//			predicateList.add(p);
		}
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);	
		return getEntityManager().createQuery(query).getResultList();

	}
}
