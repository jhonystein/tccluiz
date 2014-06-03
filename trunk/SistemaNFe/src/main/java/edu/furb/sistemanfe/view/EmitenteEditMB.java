
package edu.furb.sistemanfe.view;

import java.io.File;
import java.io.FileOutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.business.EmitenteBC;
import edu.furb.sistemanfe.business.LeitorXMLEmitente;
import edu.furb.sistemanfe.configuration.AppConfig;
import edu.furb.sistemanfe.domain.ArquivoXML;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.exception.ValidationException;

@ViewController
@PreviousView("./emitente_list.jsf")
public class EmitenteEditMB extends AbstractEditPageBean<Emitente, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmitenteBC emitenteBC;
	@Inject
	private AppConfig appConfig;
	
	@Inject
	private FacesContext facesContext;
	
	private UploadedFile logoFooter;	

	
	@Override
	@Transactional
	public String delete() {
		this.emitenteBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.emitenteBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.emitenteBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected Emitente handleLoad(Long id) {
		return this.emitenteBC.load(id);
	}
	
	@Transactional()
	public void handleFileUpload(FileUploadEvent event) {
		
		
		logoFooter = event.getFile();
		try{
			if(!logoFooter.getContentType().equals("text/xml")){
				throw new Exception(String.format("Formato do arquivo %s não é válido.", event.getFile().getFileName()));
			}
			if(logoFooter.getSize() > appConfig.getMaxFileSize()){
				facesContext
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Atenção", String.format("Tamanho do arquivo %s é superior ao limite.", event.getFile().getFileName())));
				return;
			}
			try{
				boolean ret = emitenteBC.importarEmitenteXML(logoFooter.getContents());
				
			}catch(ValidationException vex){
				facesContext
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Atenção", "Falha ao inportar Emitente:"+vex.getMessage()));
			}
			
		}catch(Exception ex){
			facesContext
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Atenção", ex.getMessage()));
		}	

	}
}