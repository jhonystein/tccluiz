
package edu.furb.sistemanfe.view;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.ClienteBC;
import edu.furb.sistemanfe.business.EmitenteBC;
import edu.furb.sistemanfe.business.NotaFiscalBC;
import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.NotaFiscal;

@ViewController
@PreviousView("./notaFiscal_list.jsf")
public class NotaFiscalEditMB extends AbstractEditPageBean<NotaFiscal, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private NotaFiscalBC notaFiscalBC;
	

	@Inject
	private EmitenteBC emitenteBC;
	
	public List<Emitente> getEmitenteList(){
		return emitenteBC.findAll();
	}
			
	@Inject
	private ClienteBC clienteBC;
	
	public List<Cliente> getClienteList(){
		return clienteBC.findAll();
	}
			
	
	@Override
	@Transactional
	public String delete() {
		this.notaFiscalBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.notaFiscalBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.notaFiscalBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected NotaFiscal handleLoad(Long id) {
		return this.notaFiscalBC.load(id);
	}	
}