package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.rest.ClienteDTO;

@PersistenceController
public class ClienteDAO extends JPACrud<Cliente, Long> {

	private static final long serialVersionUID = 1L;

	public List<Cliente> pesquisar(ClienteDTO clienteDTO) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
		Root<Cliente> cliente = query.from(Cliente.class);
		query.select(cliente);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (clienteDTO.getId() != null) {
			Predicate p = builder.equal(cliente.<Long> get("id"),
					clienteDTO.getId());
			predicateList.add(p);
		}
		if (clienteDTO.getDocumento() != null) {
			Predicate p = builder.equal(cliente.<String> get("documento"),
					clienteDTO.getDocumento());
			predicateList.add(p);
		}
		//TODO: mimplementar os demais campos;
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);	
		return getEntityManager().createQuery(query).getResultList();

	}
}
