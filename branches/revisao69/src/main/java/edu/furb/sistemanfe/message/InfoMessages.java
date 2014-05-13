package edu.furb.sistemanfe.message;

import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;

public interface InfoMessages {
	final Message ARQUIVOXML_DELETE_OK = new DefaultMessage("{arquivoxml-delete-ok}");
    final Message ARQUIVOXML_INSERT_OK = new DefaultMessage("{arquivoxml-insert-ok}");
    final Message ARQUIVOXML_UPDATE_OK = new DefaultMessage("{arquivoxml-update-ok}");
    
}
