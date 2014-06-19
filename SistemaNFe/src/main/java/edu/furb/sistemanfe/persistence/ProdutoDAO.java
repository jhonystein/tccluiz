package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
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
import edu.furb.sistemanfe.rest.ProdutoDTO;

@PersistenceController
public class ProdutoDAO extends Crud<Produto, Long, ProdutoDTO> {

	private static final long serialVersionUID = 1L;

//	public List<Produto> pesquisar(ProdutoDTO dto) {
//		return montaQuery(dto).getResultList();
//	}
	
//	private TypedQuery<Produto> montaQuery(ProdutoDTO dto){
//		return this.montaQuery(dto, null, null);
//	}
//	
//	private TypedQuery<Produto> montaQuery(ProdutoDTO dto, String sortField,
//			SortOrder sortOrder){
//		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
//		Root<Produto> objeto = query.from(Produto.class);
//		query.select(objeto);
//		
//		if(sortField!=null){
//			if((sortOrder == null)||sortOrder.equals(SortOrder.ASCENDING)){
//				query.orderBy(builder.asc(objeto.get(sortField)));
//			}else{
//				query.orderBy(builder.desc(objeto.get(sortField)));
//			}			
//		}
//
//		List<Predicate> predicateList = new ArrayList<Predicate>();
//
//		if (dto.getId() != null) {
//			Predicate p = builder.equal(objeto.<Long> get("id"), dto.getId());
//			predicateList.add(p);
//		}
//		if (dto.getCodigo() != null) {
//			Predicate p = builder.equal(objeto.<String> get("codigo"),
//					dto.getCodigo());
//			predicateList.add(p);
//		}
//		if (dto.getNome() != null) {
//			Predicate p = builder.equal(objeto.<String> get("nome"),
//					dto.getNome());
//			predicateList.add(p);
//		}
//		/**
//		 * Sempre usa o critério de emitente atual na consulta
//		 */
//		Predicate p = builder.equal(objeto.<Emitente> get("emitente"),
//				dto.getEmitente());
//		predicateList.add(p);
//
//		Predicate[] predicates = new Predicate[predicateList.size()];
//		predicateList.toArray(predicates);
//		query.where(predicates);
//				
//		return getEntityManager().createQuery(query);
//	}
	

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

	public List<ProdutoCurvaABC> produtosABC(Emitente emitente) {
		/*
		 * ProdutoCurvaABC(String codigo, String nome, Double valorUnitario,
		 * Double quantidade)
		 */
		String sqlQuery = "SELECT "
				+ " new edu.furb.sistemanfe.pojo.ProdutoCurvaABC(p.codigo, p.nome, sum(i.valorUnitario), "
				+ " sum(i.quantidade)) "
				+ "  from NotaFiscal as n, Produto as p "
				+ "  join n.itemNotaFiscal as i "
				+ "  where p.codigo = i.produtoNotaFiscal.codigo and "
				+ " p.emitente = n.emitente and n.emitente = ?1 "
				+ "  group by p.codigo, p.nome order by sum(i.valorUnitario) * sum(i.quantidade) desc ";
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, ProdutoCurvaABC.class);
		query3.setParameter(1, emitente);
		List<ProdutoCurvaABC> produtos = (List<ProdutoCurvaABC>) query3
				.getResultList();
		System.out.println(produtos.toString());
		return produtos;

		/*
		 * select SUM(I.VLUNITARIO) as VALORUNITARIO, SUM(I.VLQUANTIDADE) as
		 * QUANTIDADE, SUM(I.VLUNITARIO) * SUM(I.VLQUANTIDADE) AS CONSUMIDO,
		 * P.DSCODIGO as CODIGO, P.NMPRODUTO as PRODUTO from TBITEMNOTAFISCAL as
		 * I JOIN TBNOTAFISCAL as N on N.IDNOTAFISCAl = I.IDNOTAFISCAL_ID JOIN
		 * TBPRODUTO P on P.DSCODIGO = I.DSCODIGO and P.IDEMITENTE =
		 * N.IDEMITENTE -- where -- N.IDEMITENTE = 100 and N.DTEMISSAO >= '' and
		 * N.DTEMISSAO <= '' group by P.DSCODIGO, P.NMPRODUTO order by 3 DESC;
		 */

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
		//ProdutoDTO pDTO = (ProdutoDTO)dto;
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

}
