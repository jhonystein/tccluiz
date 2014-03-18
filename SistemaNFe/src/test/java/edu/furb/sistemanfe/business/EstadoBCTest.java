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
import edu.furb.sistemanfe.domain.Pais;

@RunWith(DemoiselleRunner.class)
public class EstadoBCTest {
	@Inject
	private EstadoBC estadoBC;
	@Inject
	private PaisBC paisBC;
	
	@Before
	public void before() {
		for (Estado estado : estadoBC.findAll()) {
			estadoBC.delete(estado.getId());
		}
		paisBC.load();
	}

	@Test
	public void testLoad() {
		estadoBC.load();
		List<Estado> listaEstados = estadoBC.findAll();
		assertNotNull(listaEstados);
		assertEquals(2, listaEstados.size());
	}
	
	@Test
	public void testInsert() {
		Pais pais = paisBC.findAll().get(0);
				
		Estado estado = new Estado("SC", "Santa Catarina", "123", pais);
		estadoBC.insert(estado);
		List<Estado> listaEstados = estadoBC.findAll();
		//Identifica se a lista não esta fazia.
		assertNotNull(listaEstados);
		//Deve exitir somenete um registro;
		assertEquals(1, listaEstados.size());
		//Garante que o país do estado obtido é o mesmo do inserido(relacionamento);
		assertEquals(pais.getId(), listaEstados.get(0).getPais().getId());
	}
}
