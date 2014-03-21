
package edu.furb.sistemanfe.view;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.ItemNotaFiscalBC;
import edu.furb.sistemanfe.business.NotaFiscalBC;
import edu.furb.sistemanfe.business.ProdutoBC;
import edu.furb.sistemanfe.domain.ItemNotaFiscal;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.domain.Produto;

@ViewController
@PreviousView("./itemNotaFiscal_list.jsf")
public class ItemNotaFiscalEditMB extends AbstractEditPageBean<ItemNotaFiscal, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ItemNotaFiscalBC itemNotaFiscalBC;
	

	@Inject
	private NotaFiscalBC notaFiscalBC;
	
	public List<NotaFiscal> getNotaFiscalList(){
		return notaFiscalBC.findAll();
	}
			
	@Inject
	private ProdutoBC produtoBC;
	
	public List<Produto> getProdutoList(){
		return produtoBC.findAll();
	}
			
	
	@Override
	@Transactional
	public String delete() {
		this.itemNotaFiscalBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.itemNotaFiscalBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.itemNotaFiscalBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected ItemNotaFiscal handleLoad(Long id) {
		return this.itemNotaFiscalBC.load(id);
	}	
}