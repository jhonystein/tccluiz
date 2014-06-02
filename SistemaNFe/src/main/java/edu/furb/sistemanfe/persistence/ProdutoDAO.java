package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.pojo.ProdutoGraficoVendas;
import edu.furb.sistemanfe.rest.ProdutoDTO;

@PersistenceController
public class ProdutoDAO extends JPACrud<Produto, Long> {

	private static final long serialVersionUID = 1L;

	public List<Produto> pesquisar(ProdutoDTO dto) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> objeto = query.from(Produto.class);
		query.select(objeto);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (dto.getId() != null) {
			Predicate p = builder.equal(objeto.<Long> get("id"), dto.getId());
			predicateList.add(p);
		}
		if (dto.getCodigo() != null) {
			Predicate p = builder.equal(objeto.<String> get("codigo"),
					dto.getCodigo());
			predicateList.add(p);
		}
		if (dto.getNome() != null) {
			Predicate p = builder.equal(objeto.<String> get("nome"),
					dto.getNome());
			predicateList.add(p);
		}
		// TODO: mimplementar os demais campos;
		// if (dto.getEmitente() == null) {
		// TODO: Deve criticar se naum tem emitente;
		// Predicate p = builder.equal(municipio.<String> get("nome"),
		// municipioDTO.getNome());
		// predicateList.add(p);
		// }

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);
		return getEntityManager().createQuery(query).getResultList();

	}

	public List<ProdutoGraficoVendas> novoTeste3(Emitente emitente) {		
		String sqlQuery = "SELECT new edu.furb.sistemanfe.pojo.ProdutoGraficoVendas(p.codigo, p.nome, sum(i.quantidade)) "
				+ " from NotaFiscal as n, Produto as p "
				+ " join n.itemNotaFiscal as i "
				+ " where p.codigo = i.produtoNotaFiscal.codigo and "
				+ " n.emitente = ?1 "
				+ " group by p.codigo, p.nome "
				+ " order by p.codigo";
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, ProdutoGraficoVendas.class);
		query3.setParameter(1, emitente);
		List<ProdutoGraficoVendas> pessoa = (List<ProdutoGraficoVendas>) query3
				.getResultList();
		System.out.println(pessoa.toString());
		return pessoa;

	}

}
