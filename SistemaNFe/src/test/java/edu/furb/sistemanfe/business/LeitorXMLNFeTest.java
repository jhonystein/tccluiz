package edu.furb.sistemanfe.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.NotaFiscal;

@RunWith(DemoiselleRunner.class)
public class LeitorXMLNFeTest {

	@Inject
	private LeitorXMLNFe nfeXmlReader;

	@Test
	public void lerXmlSucesso() {

		String caminho = "nfe.xml";

		List<NotaFiscal> litaNotas = nfeXmlReader.readXml(caminho);
		assertNotNull(litaNotas);
		assertEquals(1, litaNotas.size());
	}
}
