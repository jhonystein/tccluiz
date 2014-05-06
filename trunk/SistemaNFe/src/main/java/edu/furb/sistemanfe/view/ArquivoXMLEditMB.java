
package edu.furb.sistemanfe.view;

import java.util.Calendar;

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
import edu.furb.sistemanfe.configuration.AppConfig;
import edu.furb.sistemanfe.domain.ArquivoXML;

@ViewController
@PreviousView("./upload_file.jsf")
public class ArquivoXMLEditMB extends AbstractEditPageBean<ArquivoXML, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ArquivoXMLBC ArquivoXMLBC;	

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private AppConfig appConfig;
		
	private UploadedFile logoFooter;
	
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
		return getPreviousView();
		
//		System.out.println("INSERINDO");
//		if(logoFooter!=null){
//			this.getBean().setArquivo(logoFooter.getContents());
//			this.getBean().setNome(logoFooter.getFileName());
//			System.out.println("ARQUIVO:"+logoFooter.getFileName());
//		}else{
//			this.getBean().setArquivo(null);
//			System.out.println("NULOOOOOOO");
//		}
//		getBean().setDataUpload(Calendar.getInstance().getTime());
//		getBean().setStatus("N");
//		this.ArquivoXMLBC.insert(this.getBean());		
//		facesContext
//		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Sucesso", String.format("Arquivo XML %s inserido com sucesso! ID:%s .", this.getBean().getNome(), this.getBean().getId())  ));
//		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		facesContext
		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
				"Atenção", "Ação não permitida" ));
		return getPreviousView();
//		this.getBean().setArquivo(logoFooter.getContents());
//		this.ArquivoXMLBC.update(this.getBean());
//		facesContext
//		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Sucesso", String.format("Arquivo XML %s atualizado com sucesso!", this.getBean().getNome())  ));
//		return getPreviousView();
	}
	
	@Override
	protected ArquivoXML handleLoad(Long id) {
		return this.ArquivoXMLBC.load(id);
	}	
	
	//@Transactional()
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
			if(logoFooter!=null){
				this.getBean().setArquivo(logoFooter.getContents());
				this.getBean().setNome(logoFooter.getFileName());
			}else{
				this.getBean().setArquivo(null);
			}
			
			this.insert();
			
		}catch(Exception ex){
			facesContext
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Atenção", ex.getMessage()));
		}		
//		
//		String type = (String) event.getComponent().getAttributes().get("type");
//		UploadedFile logoFooter = event.getFile();
//		String nome = logoFooter.getFileName();
//		System.out.println("TESTE: "+ nome);
//		System.out.println("tipo: "+ type);
//		
//		
//		
//		UploadedFile uploadedFile = event.getFile();
//		getBean().setArquivo(uploadedFile.getContents());
//		getBean().setNome(nome);
//		getBean().setStatus("N");
//		
//		try{
//			this.ArquivoXMLBC.insert(this.getBean());			
//		}catch(Exception ex){
//			System.out.println("Erro: "+ ex.getMessage());
//		}		
//		if(this.getBean().getId()!=null){
//			System.out.println("ID: "+ this.getBean().getId());
//		}else{System.out.println("====SEM ID====");}
//		
////		File Banner = new File(appConfig.getUploadBasePath()
////				+ logoFooter.getFileName());
////				FileUtils.copyInputStreamToFile(logoFooter.getInputstream(),
////				Banner);
////				evento.setRutaLogoBanner(appConfig.getUploadBasePath()
////				+ logoFooter.getFileName());
//
//		facesContext
//				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//						"Información", "Logo " + event.getFile().getFileName()
//								+ " subido con éxito."));
	}
}