package edu.furb.sistemanfe.business;

import java.util.List;

import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.persistence.UsuarioDAO;
import edu.furb.sistemanfe.rest.UsuarioDTO;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;


@BusinessController
	public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {
		private static final long serialVersionUID = 1L;	

		public Usuario buscaLogin(String login) {
			UsuarioDTO dto = new UsuarioDTO();
			dto.setLogin(login);
			List<Usuario> lst = getDelegate().pesquisar(dto);
			if((lst == null) || (lst.size() == 0)){
				return null;
			}
			return lst.get(0);	
		}

}


