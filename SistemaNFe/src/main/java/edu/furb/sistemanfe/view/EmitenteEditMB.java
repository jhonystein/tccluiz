
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
import edu.furb.sistemanfe.business.EmitenteBC;
import edu.furb.sistemanfe.configuration.AppConfig;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.exception.ValidationException;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@ViewController
@PreviousView("./emitente_list.jsf")
public class EmitenteEditMB extends AbstractEditPageBean<Emitente, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmitenteBC emitenteBC;
	@Inject
	private AppConfig appConfig;
	@Inject
	private SistemaNFeCredentials credentials;
	
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
	
	/**
	 * Contem a regra que controla a exibição do componente de UPLOAD no cadastro do emitente.
	 * @return true(se não tiver Emitente assocuado ao Usuário atual)/false(caso contrário);
	 */
	public boolean getExibirUpload(){
		//TODO: FALTA testar esta implementação;
		/**
		 * Somente exibe o componente se não tiver Emitente assocuado ao usuário
		 */
		return credentials.getUsuario().getEmitente()==null;
	}

	@Override
	protected Emitente handleLoad(Long id) {
		return this.emitenteBC.load(id);
	}

	@Transactional()
	public void handleFileUpload(FileUploadEvent event) {
		//TODO: FALTA testar esta implementação;
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
				emitenteBC.importarEmitenteXML(logoFooter.getContents());
				facesContext
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Emitente importado com sucesso!!"));

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