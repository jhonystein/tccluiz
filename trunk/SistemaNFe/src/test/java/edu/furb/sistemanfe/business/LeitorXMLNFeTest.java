package edu.furb.sistemanfe.business;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Endereco;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.exception.ValidationException;

@RunWith(DemoiselleRunner.class)
public class LeitorXMLNFeTest {

	@Inject
	private LeitorXMLNFe nfeXmlReader;
	@Inject
	private EmitenteBC emitenteBC;
	private Emitente emitenteTest;

	@Before
	public void before() {

		emitenteTest = emitenteBC.buscaDocumento("000000000000");
		if (emitenteTest == null) {

			Endereco endEmit = new Endereco();
			endEmit.setBairro("Bairro");
			endEmit.setCep("CEP");
			endEmit.setNumero("numero");
			emitenteTest = new Emitente("000000000000",
					"EMITENTE PARA ROTINA DE TESTES", "ISENTO", endEmit);
			emitenteTest = emitenteBC.insert(emitenteTest);
		}
	}

	@After
	public void after() {
		//
	}

	@Test
	public void lerXmlSucesso() {

		String caminho = "nfe.xml";
		//nfeXmlReader.setEmitente(emitenteTest);
		NotaFiscal litaNotas = null;
		try {
			litaNotas = nfeXmlReader.readXml(caminho);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(litaNotas);
	}
}
