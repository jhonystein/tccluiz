
package edu.furb.sistemanfe.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.ArquivoXMLBC;
import edu.furb.sistemanfe.domain.ArquivoXML;


@ViewController
@PreviousView("./upload_file.jsf")
public class ArquivoXMLEditMB extends AbstractEditPageBean<ArquivoXML, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ArquivoXMLBC ArquivoXMLBC;
	

	@Inject
	private FacesContext facesContext;
		
	
	@Override
	@Transactional
	public String delete() {
		this.ArquivoXMLBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.ArquivoXMLBC.insert(this.getBean());
		facesContext
		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Información", String.format("Arquivo XML %s inserido com sucesso!", this.getBean().getNome())  ));
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.ArquivoXMLBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected ArquivoXML handleLoad(Long id) {
		return this.ArquivoXMLBC.load(id);
	}	
	
	public void handleFileUpload(FileUploadEvent event) {

		String type = (String) event.getComponent().getAttributes().get("type");
		UploadedFile logoFooter = event.getFile();
		String nome = logoFooter.getFileName();
		System.out.println("TESTE: "+ nome);
		System.out.println("tipo: "+ type);
		
		UploadedFile uploadedFile = event.getFile();
		getBean().setArquivo(uploadedFile.getContents());
		getBean().setNome(nome);
		getBean().setStatus("N");
		
		this.ArquivoXMLBC.insert(this.getBean());
		if(this.getBean().getId()!=null){
			System.out.println("ID: "+ this.getBean().getId());
		}else{System.out.println("====SEM ID====");}
		
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
}