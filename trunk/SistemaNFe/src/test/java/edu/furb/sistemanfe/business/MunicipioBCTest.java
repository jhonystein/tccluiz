

package edu.furb.sistemanfe.business;

import static org.junit.Assert.*;
import java.util.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Municipio;
import edu.furb.sistemanfe.business.MunicipioBC;

@RunWith(DemoiselleRunner.class)
public class MunicipioBCTest {

    @Inject
	private MunicipioBC municipioBC;
	
	@Before
	public void before() {
		for (Municipio municipio : municipioBC.findAll()) {
			municipioBC.delete(municipio.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Municipio municipio = new Municipio("codigoIbge","nome",null);
		
		municipioBC.insert(municipio);
		List<Municipio> listOfMunicipio = municipioBC.findAll();
		assertNotNull(listOfMunicipio);
		assertEquals(1, listOfMunicipio.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Municipio municipio = new Municipio("codigoIbge","nome",null);
		municipioBC.insert(municipio);
		
		List<Municipio> listOfMunicipio = municipioBC.findAll();
		assertNotNull(listOfMunicipio);
		assertEquals(1, listOfMunicipio.size());
		
		municipioBC.delete(municipio.getId());
		listOfMunicipio = municipioBC.findAll();
		assertEquals(0, listOfMunicipio.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Municipio municipio = new Municipio("codigoIbge","nome",null);
		municipioBC.insert(municipio);
		
		List<Municipio> listOfMunicipio = municipioBC.findAll();
		Municipio municipio2 = (Municipio)listOfMunicipio.get(0);
		assertNotNull(listOfMunicipio);

		// alterar para tratar uma propriedade existente na Entidade Municipio
		// municipio2.setUmaPropriedade("novo valor");
		municipioBC.update(municipio2);
		
		listOfMunicipio = municipioBC.findAll();
		Municipio municipio3 = (Municipio)listOfMunicipio.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Municipio
		// assertEquals("novo valor", municipio3.getUmaPropriedade());
	}

}