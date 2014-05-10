package edu.furb.sistemanfe.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.AuthorizationException;
import br.gov.frameworkdemoiselle.security.Authorizer;
import br.gov.frameworkdemoiselle.security.SecurityContext;

@SessionScoped
public class SistemaNFeAuthorizer implements Authorizer {

	private static final long serialVersionUID = 1L;

	@Inject
	private SecurityContext context;

	@Override
	public boolean hasRole(String role) throws Exception {
		boolean authorized = false;
		try {
			String usr = context.getUser().getId();
			if (usr.equals("gerente") && role.equals("gerente")) {
				authorized = true;
			}
		} catch (Exception e) {
			throw new AuthorizationException(e.getMessage());
		}
		return authorized;
	}

	@Override
	public boolean hasPermission(String res, String op) throws Exception {
		boolean authorized = false;

		try {
			String usr = context.getUser().getId();
			if (context.hasRole("gerente")) {
				authorized = true;
			} else {
				if (usr.equals("atendente") && res.equals("automovel")
						&& op.equals("insert")) {
					authorized = true;
				}

				if (usr.equals("atendente") && res.equals("automovel")
						&& op.equals("update")) {
					authorized = true;
				}

				if (usr.equals("atendente") && res.equals("estacionamento")
						&& op.equals("insert")) {
					authorized = true;
				}

				if (usr.equals("atendente") && res.equals("estacionamento")
						&& op.equals("update")) {
					authorized = true;
				}

				if (usr.equals("atendente") && res.equals("patio")
						&& op.equals("insert")) {
					authorized = true;
				}

				if (usr.equals("atendente") && res.equals("patio")
						&& op.equals("update")) {
					authorized = true;
				}
				if (usr.equals("atendente") && res.equals("endereco")
						&& op.equals("insert")) {
					authorized = true;
				}

				if (usr.equals("atendente") && res.equals("endereco")
						&& op.equals("update")) {
					authorized = true;
				}
				if (usr.equals("atendente") && res.equals("cliente")
						&& op.equals("insert")) {
					authorized = true;
				}

				if (usr.equals("atendente") && res.equals("cliente")
						&& op.equals("update")) {
					authorized = true;
				}
			}
		} catch (Exception e) {
			throw new AuthorizationException(e.getMessage());
		}

		return authorized;
	}

}
