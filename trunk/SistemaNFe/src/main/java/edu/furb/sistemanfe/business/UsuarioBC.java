package edu.furb.sistemanfe.business;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.mail.Mail;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.enumeration.StatusUsuarioEnum;
import edu.furb.sistemanfe.enumeration.TipoUsuarioEnum;
import edu.furb.sistemanfe.persistence.UsuarioDAO;
import edu.furb.sistemanfe.rest.UsuarioDTO;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {

	private static final long serialVersionUID = 1L;
	@Inject
	private Mail mail;
	@Startup
	@Transactional
	public void load() {
		if (findByUsername("admin@sistemanfe.com.br") == null) {
			/**
			 * Sempre deve existir pelo menos um usuário ADMIN
			 */
			Usuario u = new Usuario();
			u.setLogin("admin@sistemanfe.com.br");
			u.setSenha("admin");
			u.setNome("Administrador");
			u.setStatus(StatusUsuarioEnum.ATIVO);
			u.setTipoUsuario(TipoUsuarioEnum.ADMIN);			
			insert(u);
			
			/**
			 * Iniciar um usuário padrão para testes
			 */
			u = new Usuario();
			u.setLogin("cliente@sistemanfe.com.br");
			u.setSenha("cliente");
			u.setNome("Cliente nro 1");
			u.setStatus(StatusUsuarioEnum.ATIVO);
			u.setTipoUsuario(TipoUsuarioEnum.CLIENTE);			
			insert(u);
		}
	}
	
	public void emailConfirma(Usuario usuario) {		
		
		String texto = String.format(" Prezado, %s \n \n Sua conta foi ativada com sucesso!!! \n \n Login: %s \n Senha: %s", usuario.getNome(),usuario.getLogin(), usuario.getSenha());
		mail

		.to(usuario.getLogin())

		.from("contatosistemanfe@gmail.com")

		.body().text(texto)

		.subject("Ativação de conta Sistema NF-e")

		//.importance().high()

		.send();

	}
	/***
	 * Obtem uma lista de objetos para popular um Select do tipo Tipo Admin
	 * @return Lista de objetos
	 */	
	public List<SelectItem> getTiposAdministrador() {
		List<SelectItem> autoTipos = new ArrayList<SelectItem>();

		for (TipoUsuarioEnum tipoAdministrador : TipoUsuarioEnum
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

	public Usuario findByUsername(String username) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setLogin(username);
		List<Usuario> resp = getDelegate().pesquisar(dto);
		return ((resp==null)||(resp.size()==0))?(null):(resp.get(0));
	}

}
