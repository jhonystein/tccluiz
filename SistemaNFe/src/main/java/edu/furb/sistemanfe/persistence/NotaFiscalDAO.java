package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.pojo.ClienteVendas;
import edu.furb.sistemanfe.pojo.RegiaoVendas;
import edu.furb.sistemanfe.rest.NotaFiscalDTO;

@PersistenceController
public class NotaFiscalDAO extends JPACrud<NotaFiscal, Long> {

	private static final long serialVersionUID = 1L;

	public List<NotaFiscal> pesquisar(NotaFiscalDTO notaFiscalDTO) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<NotaFiscal> query = builder.createQuery(NotaFiscal.class);
		Root<NotaFiscal> notaFiscal = query.from(NotaFiscal.class);
		query.select(notaFiscal);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (notaFiscalDTO.getId() != null) {
			Predicate p = builder.equal(notaFiscal.<Long> get("id"),
					notaFiscalDTO.getId());
			predicateList.add(p);
		}
		if (notaFiscalDTO.getChaveNfe() != null) {
			Predicate p = builder.equal(notaFiscal.<String> get("chaveNfe"),
					notaFiscalDTO.getChaveNfe());
			predicateList.add(p);
		}
		if (notaFiscalDTO.getNumero() != null) {
			Predicate p = builder.equal(notaFiscal.<String> get("numero"),
					notaFiscalDTO.getNumero());
			predicateList.add(p);
		}
		if (notaFiscalDTO.getSerie() != null) {
			Predicate p = builder.equal(notaFiscal.<String> get("serie"),
					notaFiscalDTO.getSerie());
			predicateList.add(p);
		}
		if (notaFiscalDTO.getEmitente() != null) {
			Predicate p = builder.equal(notaFiscal.<Emitente> get("emitente"),
					notaFiscalDTO.getEmitente());
			predicateList.add(p);
		}
		try {
			Predicate[] predicates = new Predicate[predicateList.size()];
			predicateList.toArray(predicates);
			query.where(predicates);
			return getEntityManager().createQuery(query).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<RegiaoVendas> regiaoVendas(Emitente emitente, Date dataIni,
			Date dataFim) {
		String sqlQuery = "SELECT "
				+ " new edu.furb.sistemanfe.pojo.RegiaoVendas(m, "
				+ " count(m.id), sum(n.valorTotalNota)) "
				+ "  from NotaFiscal as n "
				+ "  join n.endereco.municipio as m"
				+ " where  "
				+ "  n.emitente = ?1 and "
				+ "  n.dataEmissao between ?2 and ?3 "
				+ "  group by m order by m.nome asc ";
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, RegiaoVendas.class);
		query3.setParameter(1, emitente);
		query3.setParameter(2, dataIni);
		query3.setParameter(3, dataFim);
		List<RegiaoVendas> lstRetorno = (List<RegiaoVendas>) query3
				.getResultList();
		return lstRetorno;
	}
}
