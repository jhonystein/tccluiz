package edu.furb.sistemanfe.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.ClienteBC;
import edu.furb.sistemanfe.business.EmitenteBC;
import edu.furb.sistemanfe.business.NotaFiscalBC;
import edu.furb.sistemanfe.configuration.AppConfig;
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
	//@Inject
	//private AppConfig appConfig;
	private String logoMaxFileSize = "1048576";

	@Inject
	private ClienteBC clienteBC;
	@Inject
	private FacesContext facesContext;

//	@PostConstruct
//	public void init() {
//		logoMaxFileSize = appConfig.getLogoMaxFileSize();
//	}

	public List<Emitente> getEmitenteList() {
		return emitenteBC.findAll();
	}

	public List<Cliente> getClienteList() {
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

	public void handleFileUpload(FileUploadEvent event) {

		String type = (String) event.getComponent().getAttributes().get("type");
		NotaFiscal evento = getBean();
		UploadedFile logoFooter = event.getFile();
		String nome = logoFooter.getFileName();
		System.out.println("TESTE: "+ nome);
		System.out.println("tipo: "+ type);
//		File Banner = new File(appConfig.getUploadBasePath()
//				+ logoFooter.getFileName());
//				FileUtils.copyInputStreamToFile(logoFooter.getInputstream(),
//				Banner);
//				evento.setRutaLogoBanner(appConfig.getUploadBasePath()
//				+ logoFooter.getFileName());

		facesContext
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Información", "Logo " + event.getFile().getFileName()
								+ " subido con éxito."));
	}

	public String getLogoMaxFileSize() {
		System.out.println("Obter tamanho maximo");
		facesContext
		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Información", "TESTE"));
		return logoMaxFileSize;
	}

	public void setLogoMaxFileSize(String logoMaxFileSize) {
		this.logoMaxFileSize = logoMaxFileSize;
	}

}