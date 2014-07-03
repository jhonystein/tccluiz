package edu.furb.sistemanfe.view;

import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.UsuarioBC;
import edu.furb.sistemanfe.domain.Usuario;

@ViewController
@PreviousView("./usuario_list.jsf")
public class UsuarioEditMB extends AbstractEditPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;
	@Inject
	private MessageContext messageContext;
	
	public List<SelectItem> getTiposAdministrador() {
		return usuarioBC.getTiposAdministrador();
	}

	public List<SelectItem> getStatusUsuario() {
		return usuarioBC.getStatusUsuario();
	}

	@Override
	@Transactional
	public String delete() {
		this.usuarioBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.usuarioBC.insert(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.usuarioBC.update(this.getBean());
		return getPreviousView();
	}

	@Override
	protected Usuario handleLoad(Long id) {
		return this.usuarioBC.load(id);
	}
	
	public void emailConfirma(){
		try {
			usuarioBC.emailConfirma(getBean());
			messageContext
			.add("Email enviado", SeverityType.INFO);
		} catch (Exception ex) {
			messageContext
					.add("Erro ao solicitar envio de email: {0}", SeverityType.ERROR, ex.getMessage());
		}
	}

	
}
