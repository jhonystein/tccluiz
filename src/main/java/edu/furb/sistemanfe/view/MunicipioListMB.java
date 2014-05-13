package edu.furb.sistemanfe.view;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.MunicipioBC;
import edu.furb.sistemanfe.domain.Municipio;

@ViewController
@NextView("./municipio_edit.jsf")
@PreviousView("./municipio_list.jsf")
public class MunicipioListMB extends AbstractListPageBean<Municipio, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private MunicipioBC municipioBC;
	
	@Override
	protected List<Municipio> handleResultList() {
		return this.municipioBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				municipioBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}