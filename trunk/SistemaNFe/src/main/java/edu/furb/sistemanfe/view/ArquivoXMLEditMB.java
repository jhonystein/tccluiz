
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
import edu.furb.sistemanfe.business.ArquivoXMLBC;
import edu.furb.sistemanfe.business.LeitorXMLNFe;
import edu.furb.sistemanfe.configuration.AppConfig;
import edu.furb.sistemanfe.domain.ArquivoXML;

@ViewController
@PreviousView("./upload_file.jsf")
public class ArquivoXMLEditMB extends AbstractEditPageBean<ArquivoXML, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ArquivoXMLBC arquivoXMLBC;	

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private AppConfig appConfig;
		
	private UploadedFile logoFooter;
	
	@Override
	@Transactional
	public String delete() {
		this.arquivoXMLBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.arquivoXMLBC.insert(this.getBean());
		return getPreviousView();
	
	}
	
	
	public void testeImportar(){
		LeitorXMLNFe ler = new LeitorXMLNFe(); 
		List<ArquivoXML> lista=this.arquivoXMLBC.findAll();
		for (ArquivoXML arquivoXML : lista) {
			ler.readXml(arquivoXML);
		}
	}
	
	@Override
	@Transactional
	public String update() {
		facesContext
		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
				"Atenção", "Ação não permitida" ));
		return getPreviousView();
	}
	
	@Override
	protected ArquivoXML handleLoad(Long id) {
		return this.arquivoXMLBC.load(id);
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
			ArquivoXML arq = this.createBean();
			if(logoFooter!=null){
				arq.setArquivo(logoFooter.getContents());
				arq.setNome(logoFooter.getFileName());
			}else{
				arq.setArquivo(null);
			}
			this.arquivoXMLBC.insert(arq);
			this.arquivoXMLBC.enviarArquivoProcessamento(arq);

		}catch(Exception ex){
			facesContext
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Atenção", ex.getMessage()));
		}	

	}
}