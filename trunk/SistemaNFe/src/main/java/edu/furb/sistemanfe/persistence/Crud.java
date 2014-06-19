package edu.furb.sistemanfe.persistence;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.template.JPACrud;

/**
 * 
 * @author Luiz
 *
 * @param <T> Classe de entidade(Bean)
 * @param <I> Tipo do id do Bean  
 * @param <DTO> Classe de consulta que extende de BaseDTO
 */
public abstract class Crud<T, I, DTO> extends JPACrud<T, I> {

	private static final long serialVersionUID = -5178684876671797772L;

	protected TypedQuery<T> montaQuery(DTO dto){
		return this.montaQuery(dto, null, null);
	}
	
	public List<T> pesquisar(DTO dto) {
		return montaQuery(dto).getResultList();
	}
	
	protected TypedQuery<T> montaQuery(DTO dto, String sortField, 
			SortOrder sortOrder){
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(this.getBeanClass());
		
		Root<T> objeto = query.from(this.getBeanClass());
		query.select(objeto);
		
		if(sortField!=null){
			if((sortOrder == null)||sortOrder.equals(SortOrder.ASCENDING)){
				query.orderBy(builder.asc(objeto.get(sortField)));
			}else{
				query.orderBy(builder.desc(objeto.get(sortField)));
			}			
		}

		List<Predicate> predicateList = montaPredicadosConsulta(dto, builder, objeto);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);
				
		return getEntityManager().createQuery(query);
	}
	
	/**
	 * 
	 * @param dto
	 * @param builder
	 * @param objeto
	 * @return
	 */
	protected abstract List<Predicate> montaPredicadosConsulta(DTO dto, CriteriaBuilder builder, Root<T> objeto);
	
	public List<T> paginacao(TypedQuery<T> listQuery){
		if (getPagination() != null) {
			Query query = getEntityManager().createQuery("SELECT COUNT(this) FROM " + this.getBeanClass().getSimpleName() + " this");
			Number cResults = (Number) query.getSingleResult();
			getPagination().setTotalResults(cResults.intValue());
			listQuery.setFirstResult(getPagination().getFirstResult());
			listQuery.setMaxResults(getPagination().getPageSize());
		}

		return listQuery.getResultList();
	}
	
}
