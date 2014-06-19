package edu.furb.sistemanfe.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.mail.Mail;
import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ManagementController;
import edu.furb.sistemanfe.business.UsuarioBC;
import edu.furb.sistemanfe.domain.Usuario;

@Named
@ManagementController
public class NovoUsuarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5678102207613023117L;

	private Usuario usuario;
	private String confirmaSenha;

	@Inject
	private MessageContext messageContext;
	@Inject
	private UsuarioBC usuarioBC;
	@Inject
	private Mail mail;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	@PostConstruct
	private void ini() {
		this.usuario = new Usuario();
	}

	private void email(Usuario usuario) {
		String texto = String.format(" Cliente %s solicitou cadastro no sistema. \n Email: %s \n Telefone: %s", usuario.getNome(),usuario.getLogin(), usuario.getFone());
		mail

		.to("contatosistemanfe@gmail.com")

		.from("contatosistemanfe@gmail.com")

		.body().text(texto)

		.subject("Solicitação de Acesso")

		//.importance().high()

		.send();

	}

	public String cadastrarNew() {
		try {
			if (!this.getUsuario().getSenha().equals(confirmaSenha)) {

				throw new Exception("Senha não confere.");
			}
			if (this.usuarioBC.findByUsername(this.getUsuario().getLogin()) != null) {
				throw new Exception("Usuário já cadastrado.");
			}
			// Usuario usuario = new Usuario();
			// usuario.setLogin(login);
			// usuario.setSenha(senha);
			this.usuarioBC.insert(usuario);
			this.email(usuario);
			messageContext.add("Solicitação fetuada com sucesso!!!",
					SeverityType.INFO);
			return "login";

		} catch (Exception ex) {
			messageContext
					.add("Erro: {0}", SeverityType.ERROR, ex.getMessage());
			return "usuario_new";
		}
	}

}
