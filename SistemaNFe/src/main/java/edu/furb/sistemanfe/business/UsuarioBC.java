package edu.furb.sistemanfe.business;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.enumeration.StatusUsuarioEnum;
import edu.furb.sistemanfe.enumeration.TipoAdministradorEnum;
import edu.furb.sistemanfe.persistence.UsuarioDAO;
import edu.furb.sistemanfe.rest.UsuarioDTO;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {

	private static final long serialVersionUID = 1L;

	/***
	 * Obtem uma lsita de objetos para popular um Select do tipo Tipo Admin
	 * @return Lista de objetos
	 */	
	public List<SelectItem> getTiposAdministrador() {
		List<SelectItem> autoTipos = new ArrayList<SelectItem>();

		for (TipoAdministradorEnum tipoAdministrador : TipoAdministradorEnum
				.values()) {

			autoTipos.add(new SelectItem(tipoAdministrador, tipoAdministrador
					.getDescricao()));
		}

		return autoTipos;
	}

	/***
	 * Obtem uma lsita de objetos para popular um Select do tipo Status Usuário
	 * @return Lista de objetos
	 */	
	public List<SelectItem> getStatusUsuario() {
		List<SelectItem> autoTipos = new ArrayList<SelectItem>();

		for (StatusUsuarioEnum statusUsuario : StatusUsuarioEnum.values()) {

			autoTipos.add(new SelectItem(statusUsuario, statusUsuario
					.getDescricao()));
		}

		return autoTipos;
	}

	public Usuario buscaLogin(String login) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setLogin(login);
		List<Usuario> lst = getDelegate().pesquisar(dto);
		if ((lst == null) || (lst.size() == 0)) {
			return null;
		}
		return lst.get(0);
	}

}
