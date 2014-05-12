package edu.furb.sistemanfe.security;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.AuthenticationException;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

@SessionScoped
public class SistemaNFeAuthenticator implements Authenticator {

	private static final long serialVersionUID = 1L;

	@Inject
	private SistemaNFeCredentials credentials;

	@Inject
	private ResourceBundle bundle;
	
	@Inject
	private FacesContext facesContext;

	private static boolean authenticated;

	@Override
	public void authenticate() throws Exception {
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		//System.out.println("Usuário:"+ ((username==null)?("NULO"):(username)) );
		//System.out.println("Senha:"+ ((password==null)?("NULO"):(password))); 
		
		if (username.equals("gerente") && password.equals("gerente")) {
			authenticated = true;
		} else if (username.equals("atendente") && password.equals("atendente")) {
			authenticated = true;
		}
		if (!authenticated) {
			throw new AuthenticationException(
					bundle.getString("usuarioNaoAutenticado"));
		}
		facesContext
		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Sucesso", String.format("Bem vindo %s!", username)));

	}

	@Override
	public void unauthenticate() throws Exception {
		credentials.clear();
	}

	@Override
	public User getUser() {
		if (authenticated) {

			return new User() {

				private static final long serialVersionUID = 1L;

				@Override
				public String getId() {
					return credentials.getUsername();
				}

				@Override
				public void setAttribute(Object key, Object value) {
				}

				@Override
				public Object getAttribute(Object key) {
					return null;
				}
			};
		} else {
			return null;
		}
	}

}
