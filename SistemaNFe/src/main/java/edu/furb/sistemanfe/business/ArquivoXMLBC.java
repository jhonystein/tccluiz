package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.ArquivoXML;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.exception.ValidationException;
import edu.furb.sistemanfe.message.ErrorMessages;
import edu.furb.sistemanfe.persistence.ArquivoXMLDAO;

@BusinessController
public class ArquivoXMLBC extends DelegateCrud<ArquivoXML, Long, ArquivoXMLDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private MessageContext messageContext;	
	@Inject
	private LeitorXMLNFe nfeXmlReader;
	@Inject
	private NotaFiscalBC notaFiscalBC;

	public void teste() {

	}

	@Override
	@Transactional
	// Não é possível com JAAS.
	//@RequiredPermission(resource = "estacionamento", operation = "insert")
	public ArquivoXML insert(ArquivoXML arquivoXML) {
		try {
			if (arquivoXML.getDataUpload() == null) {
				arquivoXML.setDataUpload(Calendar.getInstance().getTime());
			}
			arquivoXML.setStatus("N");
			super.insert(arquivoXML);
			
		} catch (Exception te) {			
			te.printStackTrace();
			messageContext.add(ErrorMessages.ARQUIVOXML_INSERT_NOK.getText(),
					te.getMessage(), SeverityType.ERROR);
			arquivoXML = null;
		}
		return arquivoXML;
	}

	/***
	 * Converte um arquivoXML em um arquivo temporáiro no Disco .
	 * 
	 * @param arquivo
	 * @return Local onde o arquivo foi gerado;
	 */
	private String converterByteParaDisco(ArquivoXML arquivo) {
		try {
			// Gera um arquivo temporário
			// TODO: Rever regra de prefixo do arquivo.
			File f = File.createTempFile(
					String.format("%s-%s", "1", arquivo.getNome()), ".XML");
			String destino = f.getAbsolutePath();
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					destino));
			fileOutputStream.write(arquivo.getArquivo());
			fileOutputStream.close();
			return destino;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Controla o envio do arquivo para processamento;
	 * 
	 * @param arquivo
	 * @throws ValidationException 
	 */
	@Transactional
	public void enviarArquivoProcessamento(ArquivoXML arquivo) throws ValidationException {
		String localArquivo = "";
		try {
			//Obtem um arquivo temporário para processamento.
			localArquivo = this.converterByteParaDisco(arquivo);
			//obtem a nota obtida do arquivo.
			NotaFiscal notaFiscal = nfeXmlReader.readXml(localArquivo);
			//TODO: trocar status para um ENUM
			arquivo.setStatus("P");
			//Associa a nota ao arquivo
			notaFiscal.setArquivoXML(arquivo);				
			notaFiscalBC.update(notaFiscal);
			arquivo.setNotaFiscal(notaFiscal);
			
			super.update(arquivo);			
			
		} finally {
			try {
				File fdel = new File(localArquivo);
				if (fdel.exists()) {
					fdel.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
