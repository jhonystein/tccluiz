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
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.pojo.ProdutoCurvaABC;
import edu.furb.sistemanfe.pojo.ProdutoGraficoVendas;
import edu.furb.sistemanfe.pojo.ProdutoVendas;
import edu.furb.sistemanfe.rest.ProdutoDTO;

@PersistenceController
public class ProdutoDAO extends Crud<Produto, Long, ProdutoDTO> {

	private static final long serialVersionUID = 1L;

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
		return pessoa;

	}

//	/**
//	 * Obtem a lista com os dados de produto para gerar a CurvaABC de produto; 
//	 * @param emitente Emitente
//	 * @return Lista de ProdutoCurvaABC com os dados preenchidos.
//	 */
//	public List<ProdutoCurvaABC> produtosABC(Emitente emitente) {
//		/*
//		 * ProdutoCurvaABC(String codigo, String nome, Double valorUnitario,
//		 * Double quantidade)
//		 */
//		String sqlQuery = "SELECT "
//				+ " new edu.furb.sistemanfe.pojo.ProdutoCurvaABC(p.codigo, p.nome, sum(i.valorUnitario), "
//				+ " sum(i.quantidade)) "
//				+ "  from NotaFiscal as n, Produto as p "
//				+ "  join n.itemNotaFiscal as i "
//				+ "  where p.codigo = i.produtoNotaFiscal.codigo and "
//				+ " p.emitente = n.emitente and n.emitente = ?1 "
//				+ "  group by p.codigo, p.nome order by sum(i.valorUnitario) * sum(i.quantidade) desc ";
//		javax.persistence.Query query3 = getEntityManager().createQuery(
//				sqlQuery, ProdutoCurvaABC.class);
//		query3.setParameter(1, emitente);
//		List<ProdutoCurvaABC> produtos = (List<ProdutoCurvaABC>) query3
//				.getResultList();
//		return produtos;
//
//	}
	/**
	 * Obtem os dados da curva ABC filtrando por emitente e período.
	 * @param emitente
	 * @param dataIni
	 * @param dataFim
	 * @return Lista de dados para curva ABC
	 */
	public List<ProdutoCurvaABC> produtosABC(Emitente emitente, Date dataIni, Date dataFim) {
		String sqlQuery = "SELECT "
				+ " new edu.furb.sistemanfe.pojo.ProdutoCurvaABC(p.codigo, p.nome, sum(i.valorUnitario), "
				+ " sum(i.quantidade)) "
				+ "  from NotaFiscal as n, Produto as p "
				+ "  join n.itemNotaFiscal as i "
				+ "  where p.codigo = i.produtoNotaFiscal.codigo and "
				+ " p.emitente = n.emitente and n.emitente = ?1  and "
				+ " n.dataEmissao between ?2 and ?3 "
				+ "  group by p.codigo, p.nome order by sum(i.valorUnitario) * sum(i.quantidade) desc ";
		
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, ProdutoCurvaABC.class);
		query3.setParameter(1, emitente);
		query3.setParameter(2, dataIni);
		query3.setParameter(3, dataFim);
		List<ProdutoCurvaABC> dadosCurvaABC = (List<ProdutoCurvaABC>) query3
				.getResultList();
		return dadosCurvaABC;

	}

	/**
	 * Busca a lista de produtos paginada
	 * @param dto Critério de filtro 
	 * @param sortField campo de ordenação
	 * @param sortOrder critério de ordenação
	 * @return Lista de produtos
	 */
	public List<Produto> buscaProdutos(ProdutoDTO dto, String sortField,
			SortOrder sortOrder) {
		TypedQuery<Produto> query = montaQuery(dto, sortField, sortOrder);
		return this.paginacao(query);
	}

	@Override
	protected List<Predicate> montaPredicadosConsulta(ProdutoDTO dto, CriteriaBuilder builder, Root<Produto> objeto) {
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
		/**
		 * Sempre usa o critério de emitente atual na consulta
		 */
		Predicate p = builder.equal(objeto.<Emitente> get("emitente"),
				dto.getEmitente());
		predicateList.add(p);
		
		return predicateList;
	}

	public List<ProdutoVendas> produtosVendas(Emitente emitente, Date dataIni,
			Date dataFim) {
		/*
		 * ProdutoVendas(Produto produto, Double quantidade, Double valor)
		 */
		String sqlQuery = "SELECT "
				+ " new edu.furb.sistemanfe.pojo.ProdutoVendas(p, sum(i.quantidade), "
				+ " sum(i.valorTotal)) "
				+ "  from NotaFiscal as n, Produto as p "
				+ "  join n.itemNotaFiscal as i "
				+ "  where p.codigo = i.produtoNotaFiscal.codigo and "
				+ " p.emitente = n.emitente and n.emitente = ?1 and "
				+ " n.dataEmissao between ?2 and ?3 "
				+ "  group by p order by sum(i.valorTotal) desc ";
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, ProdutoVendas.class);
		query3.setParameter(1, emitente);
		query3.setParameter(2, dataIni);
		query3.setParameter(3, dataFim);
		List<ProdutoVendas> produtos = (List<ProdutoVendas>) query3
				.getResultList();
		return produtos;
	}

}
