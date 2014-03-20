
package edu.furb.sistemanfe.view;

import javax.inject.Inject;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.*;
import edu.furb.sistemanfe.domain.*;
import javax.faces.model.*;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import java.util.*;

// To remove unused imports press: Ctrl+Shift+o

@ViewController
@PreviousView("./estado_list.jsf")
public class EstadoEditMB extends AbstractEditPageBean<Estado, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoBC estadoBC;
	

	@Inject
	private PaisBC paisBC;
	
	public List<Pais> getPaisList(){
		return paisBC.findAll();
	}
			
	
	@Override
	@Transactional
	public String delete() {
		this.estadoBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.estadoBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.estadoBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected Estado handleLoad(Long id) {
		return this.estadoBC.load(id);
	}	
}