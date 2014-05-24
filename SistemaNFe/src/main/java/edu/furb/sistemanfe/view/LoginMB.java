package edu.furb.sistemanfe.view;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.AuthenticationException;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractPageBean;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@ViewController
@SessionScoped
//@NextView("./index.jsf")
@NextView("./upload_file.jsf")
public class LoginMB extends AbstractPageBean {

	private static final long serialVersionUID = 1L;

	private String login = new String();
	private String senha = new String();
	
	private Usuario usuario;
	private Emitente emitente;
	

	@Inject
	private SistemaNFeCredentials credentials;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private MessageContext messageContext;
		
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public String doLogin() {
		try {
			credentials.setUsername(this.getLogin());
			credentials.setPassword(this.getSenha());
			securityContext.login();
			//System.out.println("Vai para: "+getNextView());
			return getNextView();
		} catch (AuthenticationException ae) {
			messageContext.add("Falha de autenticação: {0}", SeverityType.ERROR, ae.getMessage());
			return "./login.jsf";
		} catch (Exception e) {
			messageContext.add("Erro critico: %s", SeverityType.ERROR, e.getMessage());
			return "./login.jsf";
		}
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}

	public String doLogout() {
		//TODO: Verificar este método, parece que não esta matando a autenticação
		securityContext.logout();
		return "./login.jsf";
	
	}

}
