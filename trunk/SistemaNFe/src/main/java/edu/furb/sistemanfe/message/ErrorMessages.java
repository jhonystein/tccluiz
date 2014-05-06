package edu.furb.sistemanfe.message;

import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;

public interface ErrorMessages {

	final Message ARQUIVOXML_DELETE_NOK = new DefaultMessage("{arquivoxml-delete-nok}");
    final Message ARQUIVOXML_INSERT_NOK = new DefaultMessage("{arquivoxml-insert-nok}");
    final Message ARQUIVOXML_UPDATE_NOK = new DefaultMessage("{arquivoxml-update-nok}");
    
    final Message ARQUIVOXML_FORMAT_NOK = new DefaultMessage("{arquivoxml-format-nok}");
    final Message ARQUIVOXML_SIZE_NOK = new DefaultMessage("{arquivoxml-size-nok}");
}
