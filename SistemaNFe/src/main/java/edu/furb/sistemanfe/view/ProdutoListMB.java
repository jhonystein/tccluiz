package edu.furb.sistemanfe.view;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.ProdutoBC;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.pojo.ProdutoCurvaABC;

@ViewController
@NextView("./produto_edit.jsf")
@PreviousView("./produto_list.jsf")
public class ProdutoListMB extends AbstractListPageBean<Produto, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoBC produtoBC;

	//Paginação
	private LazyDataModel<Produto> lazyModel;

	public ProdutoListMB() {
		this.lazyModel = new LazyDataModel<Produto>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<Produto> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				Pagination pagination = getPagination();
				pagination.setPageSize(pageSize);
				pagination.setFirstResult(first);
				//long ii = ProdutoListMB.serialVersionUID; 
				//List<Produto> itensLista = produtoBC.findAll();// buscaCliente(codigo, nome, documento, sortField, sortOrder);
				List<Produto> itensLista = produtoBC.buscaProdutos(sortField, sortOrder);
				
				lazyModel.setRowCount(pagination.getTotalResults());
				return itensLista;
			}


//			@Override
//			public List<Produto> load(int first, int pageSize,
//					String sortField, SortOrder sortOrder,
//					Map<String, String> filters) {
//				
//				Pagination pagination = getPagination();
//				pagination.setPageSize(pageSize);
//				pagination.setFirstResult(first);
//				
//				List<Produto> itensLista = produtoBC.findAll();// buscaCliente(codigo, nome, documento, sortField, sortOrder);
//				
//				lazyModel.setRowCount(pagination.getTotalResults());
//				return itensLista;
//			}
		};
	}
	
	public LazyDataModel<Produto> getLazyModel() {
		return lazyModel;
	}
	
	@Override
	protected List<Produto> handleResultList() {
		return this.produtoBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				produtoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	public List<ProdutoCurvaABC> getDadosCurvaABC(){
		return produtoBC.getProdutoABC();	
	}

}