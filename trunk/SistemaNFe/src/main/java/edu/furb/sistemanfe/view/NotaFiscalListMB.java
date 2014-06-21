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
import edu.furb.sistemanfe.business.NotaFiscalBC;
import edu.furb.sistemanfe.domain.NotaFiscal;

@ViewController
@NextView("./notaFiscal_edit.jsf")
@PreviousView("./notaFiscal_list.jsf")
public class NotaFiscalListMB extends AbstractListPageBean<NotaFiscal, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private NotaFiscalBC notaFiscalBC;
	
	// Paginação
	private LazyDataModel<NotaFiscal> lazyModel;
	
	@Override
	protected List<NotaFiscal> handleResultList() {
		return this.notaFiscalBC.findAll();
	}
	
	public NotaFiscalListMB() {
		this.lazyModel = new LazyDataModel<NotaFiscal>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<NotaFiscal> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				Pagination pagination = getPagination();
				pagination.setPageSize(pageSize);
				pagination.setFirstResult(first);
				List<NotaFiscal> itensLista = notaFiscalBC.buscaNotasFiscais(sortField,
						sortOrder);

				lazyModel.setRowCount(pagination.getTotalResults());
				return itensLista;
			}
		};
	}
	
	public LazyDataModel<NotaFiscal> getLazyModel() {
		return lazyModel;
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				notaFiscalBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}