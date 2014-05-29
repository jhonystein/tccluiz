package edu.furb.sistemanfe.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import edu.furb.sistemanfe.domain.Usuario;

@SessionScoped
@Named("credenciais")
public class SistemaNFeCredentials implements Serializable {

	private static final long serialVersionUID = 3727813284989168748L;

	private String username;

	private String password;
	
	private Usuario usuario;	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void clear() {
		username = null;
		password = null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
