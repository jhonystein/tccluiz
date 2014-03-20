
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
@PreviousView("./municipio_list.jsf")
public class MunicipioEditMB extends AbstractEditPageBean<Municipio, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private MunicipioBC municipioBC;
	

	@Inject
	private EstadoBC estadoBC;
	
	public List<Estado> getEstadoList(){
		return estadoBC.findAll();
	}
			
	
	@Override
	@Transactional
	public String delete() {
		this.municipioBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.municipioBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.municipioBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected Municipio handleLoad(Long id) {
		return this.municipioBC.load(id);
	}	
}