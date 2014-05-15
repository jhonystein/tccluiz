package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.rest.UsuarioDTO;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;


@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	public List<Usuario> pesquisar(UsuarioDTO usuarioDTO) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> usuario = query.from(Usuario.class);
		query.select(usuario);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (usuarioDTO.getId() != null) {
			Predicate p = builder.equal(usuario.<Long> get("id"),
					usuarioDTO.getId());
			predicateList.add(p);
		}
		if (usuarioDTO.getLogin() != null) {
			Predicate p = builder.equal(usuario.<String> get("login"),
					usuarioDTO.getLogin());
			predicateList.add(p);
		}
		if (usuarioDTO.getSenha() != null) {
			Predicate p = builder.equal(usuario.<String> get("senha"),
					usuarioDTO.getSenha());
			predicateList.add(p);
		}
		if (usuarioDTO.getStatus() != null) {
			Predicate p = builder.equal(usuario.<String> get("status"),
					usuarioDTO.getStatus());
			predicateList.add(p);
		}
		if (usuarioDTO.getAdmin() != null) {
			Predicate p = builder.equal(usuario.<String> get("admin"),
					usuarioDTO.getAdmin());
			predicateList.add(p);
		}
	
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);	
		return getEntityManager().createQuery(query).getResultList();

	}
}
