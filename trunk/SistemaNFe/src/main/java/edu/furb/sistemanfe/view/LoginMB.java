package edu.furb.sistemanfe.view;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.AuthenticationException;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@Named
@SessionScoped
public class LoginMB implements Serializable{

	private static final long serialVersionUID = 6800185726427855295L;
	
	private Usuario usuario;
	private Emitente emitente;
	

	@Inject
	private SistemaNFeCredentials credentials;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private MessageContext messageContext;
		
	public String getNomeUsuario(){
		if(usuario==null){
			return "Sem login";
		}
		return String.format("(%s)-%s", usuario.getTipoUsuario().getDescricao(), usuario.getLogin());
	}
	
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
			securityContext.login();
			return "welcome";
		} catch (AuthenticationException ae) {
			messageContext.add("Falha de autenticação: {0}", SeverityType.ERROR, ae.getMessage());
			return "login";
		} catch (Exception e) {
			messageContext.add("Erro critico: %s", SeverityType.ERROR, e.getMessage());
			return "login";
		}
	}

	public String doLogout() {
		securityContext.logout();
		return "login";
	
	}

}
