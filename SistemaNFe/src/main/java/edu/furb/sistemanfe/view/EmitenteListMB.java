package edu.furb.sistemanfe.view;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.EmitenteBC;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@ViewController
@NextView("./emitente_edit.jsf")
@PreviousView("./emitente_list.jsf")
public class EmitenteListMB extends AbstractListPageBean<Emitente, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmitenteBC emitenteBC;
	@Inject
	private SistemaNFeCredentials cred;
	
	@Override
	protected List<Emitente> handleResultList() {
		return this.emitenteBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				emitenteBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	/**
	 * Somente permite inserir registro se ainda não possui emitente associado ao usuário
	 * @return
	 */
	public boolean getPodeInserir(){
		return cred.getUsuario().getEmitente() == null;
	}

}