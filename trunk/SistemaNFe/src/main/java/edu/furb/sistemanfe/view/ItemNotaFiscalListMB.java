package edu.furb.sistemanfe.view;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.ItemNotaFiscalBC;
import edu.furb.sistemanfe.domain.ItemNotaFiscal;

@ViewController
@NextView("./itemNotaFiscal_edit.jsf")
@PreviousView("./itemNotaFiscal_list.jsf")
public class ItemNotaFiscalListMB extends AbstractListPageBean<ItemNotaFiscal, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ItemNotaFiscalBC itemNotaFiscalBC;
	
	@Override
	protected List<ItemNotaFiscal> handleResultList() {
		return this.itemNotaFiscalBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				itemNotaFiscalBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}