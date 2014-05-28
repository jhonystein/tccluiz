package edu.furb.sistemanfe.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Emitente;

@RunWith(DemoiselleRunner.class)
public class LeitorXMLEmitenteTest {

	@Inject
	private LeitorXMLEmitente nfeXmlEmitenteReader;

	@Before
	public void before() {
	}

	@After
	public void after() {
	}

	@Test
	public void lerXmlSucesso() {
		String caminho = "nfe.xml";
		Emitente emit = nfeXmlEmitenteReader.readXml(new File(caminho));
		assertNotNull("Emitente não deveria ser nulo, já que o XML é válido", emit);
	}
	
	@Test
	public void lerXmlNaoExiste() {
		String caminho = "nfeNAOEXISTE.xml";
		Emitente emit = nfeXmlEmitenteReader.readXml(new File(caminho));
		assertNull("Como o XML não existe o emitente deveria ser nulo", emit);
	}
}
