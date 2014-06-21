package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.pojo.RegiaoVendas;
import edu.furb.sistemanfe.rest.NotaFiscalDTO;

@PersistenceController
// public class NotaFiscalDAO extends JPACrud<NotaFiscal, Long> {
public class NotaFiscalDAO extends Crud<NotaFiscal, Long, NotaFiscalDTO> {

	private static final long serialVersionUID = 1L;

	public List<RegiaoVendas> regiaoVendas(Emitente emitente, Date dataIni,
			Date dataFim) {
		String sqlQuery = "SELECT "
				+ " new edu.furb.sistemanfe.pojo.RegiaoVendas(m, "
				+ " count(m.id), sum(n.valorTotalNota)) "
				+ "  from NotaFiscal as n "
				+ "  join n.endereco.municipio as m" + " where  "
				+ "  n.emitente = ?1 and "
				+ "  n.dataEmissao between ?2 and ?3 "
				+ "  group by m order by sum(n.valorTotalNota) desc ";
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, RegiaoVendas.class);
		query3.setParameter(1, emitente);
		query3.setParameter(2, dataIni);
		query3.setParameter(3, dataFim);
		List<RegiaoVendas> lstRetorno = (List<RegiaoVendas>) query3
				.getResultList();
		return lstRetorno;
	}

	@Override
	protected List<Predicate> montaPredicadosConsulta(NotaFiscalDTO dto,
			CriteriaBuilder builder, Root<NotaFiscal> objeto) {
		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (dto.getId() != null) {
			Predicate p = builder.equal(objeto.<Long> get("id"), dto.getId());
			predicateList.add(p);
		}
		if (dto.getChaveNfe() != null) {
			Predicate p = builder.equal(objeto.<String> get("chaveNfe"),
					dto.getChaveNfe());
			predicateList.add(p);
		}
		if (dto.getNumero() != null) {
			Predicate p = builder.equal(objeto.<String> get("numero"),
					dto.getNumero());
			predicateList.add(p);
		}
		if (dto.getSerie() != null) {
			Predicate p = builder.equal(objeto.<String> get("serie"),
					dto.getSerie());
			predicateList.add(p);
		}
		/**
		 * Sempre usa o critério de emitente atual na consulta
		 */
		Predicate p = builder.equal(objeto.<Emitente> get("emitente"),
				dto.getEmitente());
		predicateList.add(p);
		return predicateList;
	}

	public List<NotaFiscal> buscaNotas(NotaFiscalDTO dto, String sortField,
			SortOrder sortOrder) {
		TypedQuery<NotaFiscal> query = montaQuery(dto, sortField, sortOrder);
		return this.paginacao(query);
	}
}
