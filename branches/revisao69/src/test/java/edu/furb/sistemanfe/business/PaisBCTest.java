package edu.furb.sistemanfe.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Pais;

@RunWith(DemoiselleRunner.class)
public class PaisBCTest {
	
	@Inject
	private PaisBC paisBC;
	
	@Before
	public void before() {
		//TODO: Aki pode dar erro pq tem  relacionamento com o Estado, ai não deiuxa exclui.
		for (Pais pais : paisBC.findAll()) {
			paisBC.delete(pais.getId());
		}
	}

	@Test
	public void testLoad() {
		paisBC.load();
		List<Pais> listaPais = paisBC.findAll();
		assertNotNull(listaPais);
		assertEquals(1, listaPais.size());
	}
	
	@Test
	public void testInsert() {
		Pais pais = new Pais("55", "Brasil");
		paisBC.insert(pais);
		List<Pais> listaPais = paisBC.findAll();
		assertNotNull(listaPais);
		assertEquals(1, listaPais.size());
	}
}
