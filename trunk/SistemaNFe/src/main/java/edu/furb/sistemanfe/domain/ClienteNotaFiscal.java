package edu.furb.sistemanfe.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Embeddable
public class ClienteNotaFiscal  {

					
		@Column(name = "NRDOCUMENTO", length = 20)
		private String documento = null;
		@Column(name = "NMDESTINATARIO", length = 100)
		private String nome = null;
		@Column(name = "NRINSCESTADUAL", length = 20)
		private String inscricaoEstadual = null;
		@Column(name = "NRFONE", length = 14)
		private String fone = null;
		@Column(name = "DSEMAIL", length = 60)
		private String email = null;
		public String getDocumento() {
			return documento;
		}
		public void setDocumento(String documento) {
			this.documento = documento;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getInscricaoEstadual() {
			return inscricaoEstadual;
		}
		public void setInscricaoEstadual(String inscricaoEstadual) {
			this.inscricaoEstadual = inscricaoEstadual;
		}
		public String getFone() {
			return fone;
		}
		public void setFone(String fone) {
			this.fone = fone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((documento == null) ? 0 : documento.hashCode());
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + ((fone == null) ? 0 : fone.hashCode());
			result = prime
					* result
					+ ((inscricaoEstadual == null) ? 0 : inscricaoEstadual
							.hashCode());
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
			ClienteNotaFiscal other = (ClienteNotaFiscal) obj;
			if (documento == null) {
				if (other.documento != null)
					return false;
			} else if (!documento.equals(other.documento))
				return false;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (fone == null) {
				if (other.fone != null)
					return false;
			} else if (!fone.equals(other.fone))
				return false;
			if (inscricaoEstadual == null) {
				if (other.inscricaoEstadual != null)
					return false;
			} else if (!inscricaoEstadual.equals(other.inscricaoEstadual))
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			return true;
		}
		
		
		
}
