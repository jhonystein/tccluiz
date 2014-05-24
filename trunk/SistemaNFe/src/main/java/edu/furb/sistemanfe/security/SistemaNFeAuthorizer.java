package edu.furb.sistemanfe.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.Authorizer;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import edu.furb.sistemanfe.enumeration.TipoUsuarioEnum;

@SessionScoped
public class SistemaNFeAuthorizer implements Authorizer {

	private static final long serialVersionUID = 6349905487640014675L;

	@Inject
	private SecurityContext context;

	@Override
	public boolean hasRole(String role) throws Exception {
		TipoUsuarioEnum tipoUsuario = TipoUsuarioEnum.valueOf(role);
		return tipoUsuario.equals(context.getUser().getAttribute("tipoUsuario"));
	}

	@Override
	public boolean hasPermission(String res, String op) throws Exception {
		return false;
	}

}
