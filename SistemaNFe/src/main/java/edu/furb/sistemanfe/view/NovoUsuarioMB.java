package edu.furb.sistemanfe.view;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.mail.Mail;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ManagementController;
import edu.furb.sistemanfe.business.UsuarioBC;
import edu.furb.sistemanfe.domain.Usuario;

@Named
@ManagementController
@RequestScoped
public class NovoUsuarioMB implements Serializable {

	private static final long serialVersionUID = 5678102207613023117L;

	private String confirmaSenha;
	private String nome;
	private String fone;
	private String login;
	private String senha;

	@Inject
	private MessageContext messageContext;
	@Inject
	private UsuarioBC usuarioBC;
	@Inject
	private Mail mail;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	private void email(Usuario usuario) {
		String texto = String
				.format(" Cliente %s solicitou cadastro no sistema. \n Email: %s \n Telefone: %s",
						usuario.getNome(), usuario.getLogin(),
						usuario.getFone());
		mail

		.to("contatosistemanfe@gmail.com")

		.from("contatosistemanfe@gmail.com")

		.body().text(texto)

		.subject("Solicitação de Acesso")

		// .importance().high()

				.send();

	}

	public String cadastrarNew() {
		try {
			if (!this.getSenha().equals(confirmaSenha)) {
				throw new Exception("Senha não confere.");
			}
			if (this.usuarioBC.findByUsername(this.getLogin()) != null) {
				throw new Exception("Usuário já cadastrado.");
			}
			Usuario usuario = new Usuario();
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setFone(fone);
			usuario.setNome(nome);

			this.usuarioBC.insert(usuario);
			this.email(usuario);
			messageContext.add("Solicitação fetuada com sucesso!!!",
					SeverityType.INFO);

			login = "";
			senha = "";
			fone = "";
			nome = "";
			return "login";

		} catch (Exception ex) {
			messageContext
					.add("Erro: {0}", SeverityType.ERROR, ex.getMessage());
			return "usuario_new";
		}
	}

}
