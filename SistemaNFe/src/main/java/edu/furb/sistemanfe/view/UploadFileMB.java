package edu.furb.sistemanfe.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractPageBean;

@ViewController
@PreviousView("./upload_file.xhtml")
public class UploadFileMB extends AbstractPageBean {

	private static final long serialVersionUID = 8123642221316061192L;

	//@Inject
	//private MessageContext messageContext;

	// public void handleFileUpload(FileUploadEvent event) {
	// // FacesMessage msg = new FacesMessage("Succesful",
	// event.getFile().getFileName() + " is uploaded.");
	// // FacesContext.getCurrentInstance().addMessage(null, msg);
	// //Message message = new
	// DefaultMessage("Ocorreu um erro ao excluir o aluno!",
	// SeverityType.ERROR);
	// messageContext.add("Upload efetiado com sucesso!!", SeverityType.INFO);
	//
	//
	// }

	public void doUpload(FileUploadEvent fileUploadEvent) {
		UploadedFile uploadedFile = fileUploadEvent.getFile();
		String fileNameUploaded = uploadedFile.getFileName();
		long fileSizeUploaded = uploadedFile.getSize(); // 3
		String infoAboutFile = "<br/> Arquivo recebido: <b>" + fileNameUploaded
				+ "</b><br/>" + "Tamanho do Arquivo: <b>" + fileSizeUploaded
				+ "</b>";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null,
				new FacesMessage("Sucesso", infoAboutFile));
	}

}
