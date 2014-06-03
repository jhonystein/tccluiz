
package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.exception.ValidationException;
import edu.furb.sistemanfe.persistence.EmitenteDAO;
import edu.furb.sistemanfe.rest.EmitenteDTO;

@BusinessController
public class EmitenteBC extends DelegateCrud<Emitente, Long, EmitenteDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LeitorXMLEmitente leitorXMLEmitente;
	
	public Emitente buscaDocumento(String documento) {
		EmitenteDTO dto = new EmitenteDTO();
		dto.setDocumento(documento);
		List<Emitente> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
	
	public boolean importarEmitenteXML(byte[] arquivo) throws ValidationException{
		String destino = "";
		File fileEmitente = null;
		// Gera um arquivo temporário		
		try {
			fileEmitente = File.createTempFile(
					String.format("%s-%s", "1", "EMITENTE"), ".XML");
			destino = fileEmitente.getAbsolutePath();
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					destino));
			
			fileOutputStream.write(arquivo);
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ValidationException("Erro ao ler arquivo:"+e.getMessage());
		}
		try{
			Emitente emit = leitorXMLEmitente.readXml(fileEmitente);
			insert(emit);
			
		}catch (Exception e) {
			throw new ValidationException("Falha ao ler arquivo XML."+e.getMessage());
		}
			
			//return destino;

		return false;
	}
}
