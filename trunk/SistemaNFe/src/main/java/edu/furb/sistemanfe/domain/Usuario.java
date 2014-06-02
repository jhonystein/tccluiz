package edu.furb.sistemanfe.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.Email;

import edu.furb.sistemanfe.enumeration.StatusUsuarioEnum;
import edu.furb.sistemanfe.enumeration.TipoUsuarioEnum;

@Entity
@Table(name = "TBUSUARIO")
@TableGenerator(name = "GenUsuario", table = "TBSEQUENCIAS", pkColumnName = "CDSEQUENCIA", pkColumnValue = "USUARIOSSEQ", valueColumnName = "VLSEQUENCIA")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -5412589210134699345L;
	@Id
	@Column(name = "IDUSUARIO")
	@GeneratedValue(generator = "GenUsuario", strategy = GenerationType.TABLE)
	private Long id = null;
	@Column(name = "NMLOGIN", length = 250, nullable=false)
	@Email
	private String login = null;
	@Column(name = "DSSENHA", length = 20, nullable=false)
	private String senha = null;
	@Column(name = "NMNOME", length = 50, nullable=true)
	private String nome = null;
	@Column(name = "DSFONE1", length = 12, nullable=true)
	private String fone1 = null;	
	@Enumerated(EnumType.STRING)
	@Column(name = "DSSTATUS", length = 20, nullable=false)
	private StatusUsuarioEnum status = StatusUsuarioEnum.BLOQUEADO;
	@Enumerated(EnumType.STRING)
	@Column(name = "DSTIPOUSUARIO", length = 20)
	private TipoUsuarioEnum tipoUsuario = TipoUsuarioEnum.CLIENTE;
	//TODO: Rever metodo de cascata. 
	@ManyToOne()
	@JoinColumn(name="IDEMITENTE")
	private Emitente emitente;
		
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFone1() {
		return fone1;
	}
	public void setFone1(String fone1) {
		this.fone1 = fone1;
	}
	public StatusUsuarioEnum getStatus() {
		return status;
	}
	public void setStatus(StatusUsuarioEnum status) {
		this.status = status;
	}
	public TipoUsuarioEnum getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public Emitente getEmitente() {
		return emitente;
	}
	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipoUsuario == null) ? 0 : tipoUsuario.hashCode());
		result = prime * result
				+ ((emitente == null) ? 0 : emitente.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	

}
