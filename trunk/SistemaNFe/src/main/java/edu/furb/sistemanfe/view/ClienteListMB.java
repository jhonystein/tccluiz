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
import edu.furb.sistemanfe.business.ClienteBC;
import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.pojo.ClienteCurvaABC;

@ViewController
@NextView("./cliente_edit.jsf")
@PreviousView("./cliente_list.jsf")
public class ClienteListMB extends AbstractListPageBean<Cliente, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteBC clienteBC;

	// Paginação
	private LazyDataModel<Cliente> lazyModel;

	public ClienteListMB() {
		this.lazyModel = new LazyDataModel<Cliente>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<Cliente> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				Pagination pagination = getPagination();
				pagination.setPageSize(pageSize);
				pagination.setFirstResult(first);
				List<Cliente> itensLista = clienteBC.buscaClientes(sortField,
						sortOrder);

				lazyModel.setRowCount(pagination.getTotalResults());
				return itensLista;
			}
		};
	}

	public LazyDataModel<Cliente> getLazyModel() {
		return lazyModel;
	}

	@Override
	protected List<Cliente> handleResultList() {
		return this.clienteBC.findAll();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				clienteBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

	public List<ClienteCurvaABC> getDadosCurvaABC() {
		return clienteBC.getDadosCurvaABC();
	}
}