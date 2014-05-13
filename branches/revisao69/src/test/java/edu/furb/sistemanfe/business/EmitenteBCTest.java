package edu.furb.sistemanfe.business;

import static org.junit.Assert.*;
import java.util.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.business.EmitenteBC;

@RunWith(DemoiselleRunner.class)
public class EmitenteBCTest {

	@Inject
	private EmitenteBC emitenteBC;

	@Before
	public void before() {
		for (Emitente emitente : emitenteBC.findAll()) {
			emitenteBC.delete(emitente.getId());
		}
	}

	@Test
	public void testInsert() {

		Emitente emitente = new Emitente("documento", "nome",
				"inscricaoEstadual", null);
		emitenteBC.insert(emitente);
		List<Emitente> listOfEmitente = emitenteBC.findAll();
		assertNotNull(listOfEmitente);
		assertEquals(1, listOfEmitente.size());
	}

	@Test
	public void testDelete() {

		// modifique para inserir dados conforme o construtor
		Emitente emitente = new Emitente("documento", "nome",
				"inscricaoEstadual", null);
		emitenteBC.insert(emitente);

		List<Emitente> listOfEmitente = emitenteBC.findAll();
		assertNotNull(listOfEmitente);
		assertEquals(1, listOfEmitente.size());

		emitenteBC.delete(emitente.getId());
		listOfEmitente = emitenteBC.findAll();
		assertEquals(0, listOfEmitente.size());
	}

	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Emitente emitente = new Emitente("documento", "nome",
				"inscricaoEstadual", null);
		emitenteBC.insert(emitente);

		List<Emitente> listOfEmitente = emitenteBC.findAll();
		Emitente emitente2 = (Emitente) listOfEmitente.get(0);
		assertNotNull(listOfEmitente);

		// alterar para tratar uma propriedade existente na Entidade Emitente
		emitente2.setNome("novo valor");
		emitenteBC.update(emitente2);

		listOfEmitente = emitenteBC.findAll();
		Emitente emitente3 = (Emitente) listOfEmitente.get(0);

		// alterar para tratar uma propriedade existente na Entidade Emitente
		assertEquals("novo valor", emitente3.getNome());
	}

}