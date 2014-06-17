
package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Usuario;
import edu.furb.sistemanfe.enumeration.TipoUsuarioEnum;
import edu.furb.sistemanfe.exception.ValidationException;
import edu.furb.sistemanfe.persistence.EmitenteDAO;
import edu.furb.sistemanfe.rest.EmitenteDTO;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@BusinessController
public class EmitenteBC extends DelegateCrud<Emitente, Long, EmitenteDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LeitorXMLEmitente leitorXMLEmitente;
	@Inject 
	private UsuarioBC usuarioBC;
	@Inject
	private SistemaNFeCredentials credentials;
	
	@Override
	public List<Emitente> findAll() {
		if(credentials.getUsuario().getTipoUsuario().equals(TipoUsuarioEnum.ADMIN)){
			return super.findAll();
		}else{
			EmitenteDTO dto = new EmitenteDTO();
			dto.setUsuario(credentials.getUsuario());
			return getDelegate().pesquisar(dto);
		}		
	}
	
	public Emitente buscaDocumento(String documento) {
		EmitenteDTO dto = new EmitenteDTO();
		dto.setDocumento(documento);
		List<Emitente> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		super.delete(id);
		credentials.getUsuario().setEmitente(null);
	}
	
	/**
	 * IMportar o emitente com base em uma rquivo XML de NF-e;
	 * @param arquivo
	 * @return
	 * @throws ValidationException
	 */
	public boolean importarEmitenteXML(byte[] arquivo) throws ValidationException{
		//TODO: FALTA testar esta implementação;
		String destino = "";
		File fileEmitente = null;
		/**
		 * Gera um arq temp do XML;
		 */
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
		/**
		 * Efetua o processamento do XML.
		 */
		try{
			//TODO: FALTA testar esta implementação toda;
			Emitente emit = leitorXMLEmitente.readXml(fileEmitente);
			/**
			 * Inseri o emitente obtido
			 */
			Usuario us = usuarioBC.findByUsername(credentials.getUsuario().getLogin());
			emit.setUsuario( us);
			emit = insert(emit);
			/**
			 * Associa ao usuário da credencial e atualiza o cadastro do usuário.
			 */
			credentials.getUsuario().setEmitente(emit);
			usuarioBC.update(credentials.getUsuario());
			
			return true;
			
		}catch (Exception e) {
			throw new ValidationException("Falha ao ler arquivo XML."+e.getMessage());
		}finally{
			/**
			 * Ao final do processamento remove o arquivo temp;
			 */
			fileEmitente.delete();
		}
	}
}
