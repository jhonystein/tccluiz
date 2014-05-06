package edu.furb.sistemanfe.business;

import java.util.Calendar;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.RequiredPermission;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.ArquivoXML;
import edu.furb.sistemanfe.message.ErrorMessages;
import edu.furb.sistemanfe.message.InfoMessages;
import edu.furb.sistemanfe.persistence.ArquivoXMLDAO;

@BusinessController
public class ArquivoXMLBC extends DelegateCrud<ArquivoXML, Long, ArquivoXMLDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private MessageContext messageContext;
		
	public void teste(){
		
	}
	
	@Override
	@Transactional
	// Não é possível com JAAS.
	@RequiredPermission(resource = "estacionamento", operation = "insert")
	public ArquivoXML insert(ArquivoXML arquivoXML) {
		try {
			if(arquivoXML.getDataUpload()==null){
				arquivoXML.setDataUpload(Calendar.getInstance().getTime());
			}
			arquivoXML.setStatus("N");
			super.insert(arquivoXML);
			messageContext.add(InfoMessages.ARQUIVOXML_INSERT_OK.getText(),
					arquivoXML.getNome());
		} catch (Exception te) {
			te.printStackTrace();
			messageContext.add(
					ErrorMessages.ARQUIVOXML_INSERT_NOK.getText(),
					te.getMessage(), SeverityType.ERROR);
		}
		return arquivoXML;
	}
}
