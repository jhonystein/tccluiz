package edu.furb.sistemanfe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "TBNOTAFISCAL")
@TableGenerator(name = "GenNotaFiscal", table = "TBSEQUENCIAS", pkColumnName = "CDSEQUENCIA", pkColumnValue = "NOTAFISCALSEQ", valueColumnName = "VLSEQUENCIA")
public class NotaFiscal {

	private static final long serialVersionUID = -2811351774276705991L;

	@Id
	@Column(name = "IDNOTAFISCAL")
	@GeneratedValue(generator = "GenNotaFiscal", strategy = GenerationType.TABLE)
	private Long id;
	@Column(name = "NRCHAVE", length = 44)
	private String chaveNfe;
	@Column(name = "DSNATOPER", length = 20)
	private String naturezaOperacao;
	@Column(name = "DSMODELO", length = 2)
	private String modelo;
	@Column(name = "NRSERIE", length = 3)
	private String serie;
	@Column(name = "NRNOTA", length = 9)
	private String numero;
	@Column(name = "DTEMISSAO", length = 20)
	private String dataEmissao;
	@Column(name = "DSTIPOEMISSAO", length = 20)
	private String tipoEmissao;
	@Column(name = "NRVALORTOTALNOTA", length = 20)
	private Double valorTotalNota;
	@Column(name = "NRVALORTOTALTRIBUTOS", length = 20)
	private Double valorTotalTributos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChaveNfe() {
		return chaveNfe;
	}
	public void setChaveNfe(String chaveNfe) {
		this.chaveNfe = chaveNfe;
	}
	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}
	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getTipoEmissao() {
		return tipoEmissao;
	}
	public void setTipoEmissao(String tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
	}
	public Double getValorTotalNota() {
		return valorTotalNota;
	}
	public void setValorTotalNota(Double valorTotalNota) {
		this.valorTotalNota = valorTotalNota;
	}
	public Double getValorTotalTributos() {
		return valorTotalTributos;
	}
	public void setValorTotalTributos(Double valorTotalTributos) {
		this.valorTotalTributos = valorTotalTributos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chaveNfe == null) ? 0 : chaveNfe.hashCode());
		result = prime * result
				+ ((dataEmissao == null) ? 0 : dataEmissao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime
				* result
				+ ((naturezaOperacao == null) ? 0 : naturezaOperacao.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result
				+ ((tipoEmissao == null) ? 0 : tipoEmissao.hashCode());
		result = prime * result
				+ ((valorTotalNota == null) ? 0 : valorTotalNota.hashCode());
		result = prime
				* result
				+ ((valorTotalTributos == null) ? 0 : valorTotalTributos
						.hashCode());
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
		NotaFiscal other = (NotaFiscal) obj;
		if (chaveNfe == null) {
			if (other.chaveNfe != null)
				return false;
		} else if (!chaveNfe.equals(other.chaveNfe))
			return false;
		if (dataEmissao == null) {
			if (other.dataEmissao != null)
				return false;
		} else if (!dataEmissao.equals(other.dataEmissao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (naturezaOperacao == null) {
			if (other.naturezaOperacao != null)
				return false;
		} else if (!naturezaOperacao.equals(other.naturezaOperacao))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (!serie.equals(other.serie))
			return false;
		if (tipoEmissao == null) {
			if (other.tipoEmissao != null)
				return false;
		} else if (!tipoEmissao.equals(other.tipoEmissao))
			return false;
		if (valorTotalNota == null) {
			if (other.valorTotalNota != null)
				return false;
		} else if (!valorTotalNota.equals(other.valorTotalNota))
			return false;
		if (valorTotalTributos == null) {
			if (other.valorTotalTributos != null)
				return false;
		} else if (!valorTotalTributos.equals(other.valorTotalTributos))
			return false;
		return true;
	}
	
	

}
