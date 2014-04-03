package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Municipio;
import edu.furb.sistemanfe.rest.MunicipioDTO;

@PersistenceController
public class MunicipioDAO extends JPACrud<Municipio, Long> {

	private static final long serialVersionUID = 1L;
	
	public List<Municipio> pesquisar(MunicipioDTO municipioDTO) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Municipio> query = builder.createQuery(Municipio.class);
		Root<Municipio> municipio = query.from(Municipio.class);
		query.select(municipio);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (municipioDTO.getId() != null) {
			Predicate p = builder.equal(municipio.<Long> get("id"),
					municipioDTO.getId());
			predicateList.add(p);
		}
		if (municipioDTO.getCodigoIbge() != null) {
			Predicate p = builder.equal(municipio.<String> get("codigoIbge"),
					municipioDTO.getCodigoIbge());
			predicateList.add(p);
		}
		if (municipioDTO.getNome() != null) {
			Predicate p = builder.equal(municipio.<String> get("nome"),
					municipioDTO.getNome());
			predicateList.add(p);
		}
		//TODO: mimplementar os demais campos;
		if (municipioDTO.getEstado() != null) {
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
