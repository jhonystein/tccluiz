package edu.furb.sistemanfe.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Estado;


@RunWith(DemoiselleRunner.class)
public class EstadoBCTest {
	@Inject
	private EstadoBC estadoBC;
	
	@Before
	public void before() {
		for (Estado estado : estadoBC.findAll()) {
			estadoBC.delete(estado.getId());
		}
	}

	@Test
	public void testLoad() {
		estadoBC.load();
		List<Estado> listaEstados = estadoBC.findAll();
		assertNotNull(listaEstados);
		assertEquals(1, listaEstados.size());
	}
	
	@Test
	public void testInsert() {
		Estado estado = new Estado("SC", "Santa Catarina", "123");
		estadoBC.insert(estado);
		List<Estado> listaEstados = estadoBC.findAll();
		assertNotNull(listaEstados);
		assertEquals(1, listaEstados.size());
	}
}
