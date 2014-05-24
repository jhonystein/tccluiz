package edu.furb.sistemanfe.security;

import java.lang.reflect.Field;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.AuthenticationException;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.frameworkdemoiselle.util.Strings;
import edu.furb.sistemanfe.business.EmitenteBC;
import edu.furb.sistemanfe.business.UsuarioBC;
import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.view.LoginMB;

@SessionScoped
public class SistemaNFeAuthenticator implements Authenticator {

	private static final long serialVersionUID = 1L;

	@Inject
	private SistemaNFeCredentials credentials;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private FacesContext facesContext;

	@Inject
	private LoginMB loginMB;

	@Inject
	private UsuarioBC usuarioBC;

	@Inject
	private EmitenteBC emitenteBC;

	private static boolean authenticated = false;

	@Override
	public void authenticate() throws Exception {
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		// if (username.equals("gerente") && password.equals("gerente")) {
		// authenticated = true;
		// } else if (username.equals("atendente") &&
		// password.equals("atendente")) {
		// authenticated = true;
		// } else if (username.equals("1") && password.equals("1")) {
		// authenticated = true;
		// }
		// if (!authenticated) {
		// throw new AuthenticationException(
		// bundle.getString("usuarioNaoAutenticado"));
		// }

		try {
			if (Strings.isEmpty(credentials.getUsername())
					|| Strings.isEmpty(credentials.getPassword())) {
				throw new AuthenticationException(
						"Credenciais não podem ser em branco!!!");
			}

			if (loginMB.getUsuario() == null) {
				Usuario usuario = usuarioBC.findByUsername(credentials
						.getUsername());
				if (usuario == null
						|| !usuario.getSenha()
								.equals(credentials.getPassword())) {
					throw new AuthenticationException(
							"Usuário/senha incorretos!!!");
				}
				loginMB.setUsuario(usuario);
				authenticated = true;

				facesContext.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
								String.format("Bem vindo %s!",
										usuario.getLogin())));
			}
		} catch (Exception e) {
			unauthenticate();
			throw e;
		}

	}

	@Override
	public void unauthenticate() throws Exception {
		authenticated = false;
		credentials.clear();
		loginMB.setUsuario(null);
		loginMB.setEmitente(null);
	}

	@Override
	public User getUser() {
		User user = null;
		if (Strings.isEmpty(credentials.getUsername())
				|| Strings.isEmpty(credentials.getPassword())) {
			return null;
		}
		Usuario usuario = null;
		if (loginMB.getUsuario() == null) {
			usuario = usuarioBC.findByUsername(credentials.getUsername());
			if (usuario == null
					|| !usuario.getSenha().equals(credentials.getPassword())) {
				return null;
			}
			loginMB.setUsuario(usuario);
		} else {
			usuario = loginMB.getUsuario();
		}

		if (usuario != null) {

			user = new User() {

				private static final long serialVersionUID = 1L;

				@Override
				public void setAttribute(Object key, Object value) {
				}

				@Override
				public String getId() {
					return loginMB.getUsuario().getId().toString();
				}

				@Override
				public Object getAttribute(Object key) {
					Usuario usuario = loginMB.getUsuario();
					try {
						Field field = usuario.getClass().getDeclaredField(
								key.toString());
						field.setAccessible(true);
						return field.get(usuario);
					} catch (NoSuchFieldException e) {
					} catch (SecurityException e) {
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					}
					return null;
				}
			};
		}
		return user;

	}

}
