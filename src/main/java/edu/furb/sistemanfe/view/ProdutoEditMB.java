
package edu.furb.sistemanfe.view;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.EmitenteBC;
import edu.furb.sistemanfe.business.ProdutoBC;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Produto;

@ViewController
@PreviousView("./produto_list.jsf")
public class ProdutoEditMB extends AbstractEditPageBean<Produto, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoBC produtoBC;
	

	@Inject
	private EmitenteBC emitenteBC;
	
	public List<Emitente> getEmitenteList(){
		return emitenteBC.findAll();
	}
			
	
	@Override
	@Transactional
	public String delete() {
		this.produtoBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.produtoBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.produtoBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected Produto handleLoad(Long id) {
		return this.produtoBC.load(id);
	}	
}