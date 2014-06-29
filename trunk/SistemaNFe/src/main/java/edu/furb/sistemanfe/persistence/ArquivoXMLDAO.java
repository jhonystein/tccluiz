package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.ArquivoXML;
import edu.furb.sistemanfe.rest.ArquivoXMLDTO;

@PersistenceController
public class ArquivoXMLDAO extends JPACrud<ArquivoXML, Long> {

	private static final long serialVersionUID = 1L;
	
	public List<ArquivoXML> pesquisar(ArquivoXMLDTO arquivoXMLDTO) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ArquivoXML> query = builder.createQuery(ArquivoXML.class);
		Root<ArquivoXML> ArquivoXML = query.from(ArquivoXML.class);
		query.select(ArquivoXML);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (arquivoXMLDTO.getId() != null) {
			Predicate p = builder.equal(ArquivoXML.<Long> get("id"),
					arquivoXMLDTO.getId());
			predicateList.add(p);
		}
		if (arquivoXMLDTO.getNome() != null) {
			Predicate p = builder.equal(ArquivoXML.<String> get("nome"),
					arquivoXMLDTO.getNome());
			predicateList.add(p);
		}
		//TODO: mimplementar os demais campos;
				
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);	
		return getEntityManager().createQuery(query).getResultList();

	}
}
