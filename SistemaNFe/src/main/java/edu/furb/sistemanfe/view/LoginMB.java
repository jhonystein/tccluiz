package edu.furb.sistemanfe.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.AuthenticationException;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractPageBean;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@ViewController
//@NextView("./index.jsf")
@NextView("./upload_file.jsf")
public class LoginMB extends AbstractPageBean {

	private static final long serialVersionUID = 1L;

	private String usuario = new String();
	private String senha = new String();

	@Inject
	private SistemaNFeCredentials credentials;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private MessageContext messageContext;

	public String doLogin() {
		try {
			credentials.setUsername(this.getUsuario());
			credentials.setPassword(this.getSenha());
			securityContext.login();
			//System.out.println("Vai para: "+getNextView());
			return getNextView();
		} catch (AuthenticationException ae) {
			messageContext.add("Falha de autenticação: {0}", SeverityType.ERROR, ae.getMessage());
			return "";
		} catch (Exception e) {
			messageContext.add("Erro critico: %s", SeverityType.ERROR, e.getMessage());
			// AuthenticationException
			return "";
		}
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}

	public void doLogout() {
		securityContext.logout();
	}

}
