package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.rest.EmitenteDTO;

@PersistenceController
public class EmitenteDAO extends JPACrud<Emitente, Long> {

	private static final long serialVersionUID = 1L;
	
	public List<Emitente> pesquisar(EmitenteDTO emitenteDTO) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Emitente> query = builder.createQuery(Emitente.class);
		Root<Emitente> emitente = query.from(Emitente.class);
		query.select(emitente);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (emitenteDTO.getId() != null) {
			Predicate p = builder.equal(emitente.<Long> get("id"),
					emitenteDTO.getId());
			predicateList.add(p);
		}
		if (emitenteDTO.getDocumento() != null) {
			Predicate p = builder.equal(emitente.<String> get("documento"),
					emitenteDTO.getDocumento());
			predicateList.add(p);
		}
		if (emitenteDTO.getInscricaoEstadual() != null) {
			Predicate p = builder.equal(emitente.<String> get("inscricaoEstadual"),
					emitenteDTO.getInscricaoEstadual());
			predicateList.add(p);
		}
		if (emitenteDTO.getNome() != null) {
			Predicate p = builder.equal(emitente.<String> get("nome"),
					emitenteDTO.getNome());
			predicateList.add(p);
		}
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);	
		return getEntityManager().createQuery(query).getResultList();

	}

}
