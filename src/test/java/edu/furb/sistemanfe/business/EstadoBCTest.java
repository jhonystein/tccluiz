

package edu.furb.sistemanfe.business;

import static org.junit.Assert.*;
import java.util.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.business.EstadoBC;

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
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Estado estado = new Estado("sigla","nome","codigoIbge",null);
		estadoBC.insert(estado);
		List<Estado> listOfEstado = estadoBC.findAll();
		assertNotNull(listOfEstado);
		assertEquals(1, listOfEstado.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Estado estado = new Estado("sigla","nome","codigoIbge",null);
		estadoBC.insert(estado);
		
		List<Estado> listOfEstado = estadoBC.findAll();
		assertNotNull(listOfEstado);
		assertEquals(1, listOfEstado.size());
		
		estadoBC.delete(estado.getId());
		listOfEstado = estadoBC.findAll();
		assertEquals(0, listOfEstado.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Estado estado = new Estado("sigla","nome","codigoIbge",null);
		estadoBC.insert(estado);
		
		List<Estado> listOfEstado = estadoBC.findAll();
		Estado estado2 = (Estado)listOfEstado.get(0);
		assertNotNull(listOfEstado);

		// alterar para tratar uma propriedade existente na Entidade Estado
		estado2.setNome("novo valor");
		estadoBC.update(estado2);
		
		listOfEstado = estadoBC.findAll();
		Estado estado3 = (Estado)listOfEstado.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Estado
		assertEquals("novo valor", estado3.getNome());
	}

}