package edu.furb.sistemanfe.rest;

import edu.furb.sistemanfe.domain.Usuario;



public class UsuarioDTO {
	private Long id = null;
	private String login = null;
	private String senha = null;
	private String status = null;
	private String admin = null;
	
	public UsuarioDTO(){		
	}
	
	public UsuarioDTO(Usuario usuario){
		super();
		this.login = usuario.getLogin();
		this.senha = usuario.getSenha();
		this.status = usuario.getStatus();
		this.admin = usuario.getAdmin();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	

}


		
	
