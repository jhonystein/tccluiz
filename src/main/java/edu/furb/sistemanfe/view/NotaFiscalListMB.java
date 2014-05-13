package edu.furb.sistemanfe.view;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
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
	
	@Override
	protected List<NotaFiscal> handleResultList() {
		return this.notaFiscalBC.findAll();
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