package edu.furb.sistemanfe.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named("credential")
public class SistemaNFeCredentials implements Serializable {

	private static final long serialVersionUID = 3727813284989168748L;

	private String username;

	private String password;

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
