package edu.furb.sistemanfe.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
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
import edu.furb.sistemanfe.exception.ValidationException;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@ViewController
@SessionScoped
@PreviousView("./upload_file.jsf")
public class ArquivoXMLEditMB extends AbstractEditPageBean<ArquivoXML, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ArquivoXMLBC arquivoXMLBC;

	@Inject
	private FacesContext facesContext;

	@Inject
	private AppConfig appConfig;

	@Inject
	private SistemaNFeCredentials cred;

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

	// public void testeImportar(){
	// LeitorXMLNFe ler = new LeitorXMLNFe();
	// List<ArquivoXML> lista=this.arquivoXMLBC.findAll();
	// for (ArquivoXML arquivoXML : lista) {
	// ler.readXml(arquivoXML);
	// }
	// }

	@Override
	@Transactional
	public String update() {
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_WARN, "Atenção", "Ação não permitida"));
		return getPreviousView();
	}

	@Override
	protected ArquivoXML handleLoad(Long id) {
		return this.arquivoXMLBC.load(id);
	}

	@Inject
	private SistemaNFeCredentials credenciais;

	@Transactional()
	public void handleFileUpload(FileUploadEvent event) {

		logoFooter = event.getFile();
		try {
			if (!logoFooter.getContentType().equals("text/xml")) {
				throw new ValidationException(String.format(
						"Formato do arquivo %s não é válido.", event.getFile()
								.getFileName()));
			}
			if (logoFooter.getSize() > appConfig.getMaxFileSize()) {
				throw new ValidationException(String.format(
						"Tamanho do arquivo %s é superior ao limite.", event
								.getFile().getFileName()));
			}
			ArquivoXML arq = this.createBean();
			if (logoFooter != null) {
				arq.setArquivo(logoFooter.getContents());
				arq.setNome(logoFooter.getFileName());
			} else {
				throw new ValidationException("Arquivo não foi recebido.");
			}

			this.arquivoXMLBC.insert(arq);
			/**
			 * Processando o arquivo
			 */
			this.arquivoXMLBC.enviarArquivoProcessamento(arq);

			facesContext.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
							String.format(
									"Nota fiscal %s importada com sucesso.",
									arq.getNotaFiscal().getChaveNfe())));
		} catch (ValidationException vex) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha",
							String.format("Falha: %s", vex.getMessage())));
		} catch (Exception ex) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							String.format("Erro: %s", ex.getMessage())));
		}

	}
}