

package edu.furb.sistemanfe.business;

import static org.junit.Assert.*;
import java.util.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.ItemNotaFiscal;
import edu.furb.sistemanfe.business.ItemNotaFiscalBC;

@RunWith(DemoiselleRunner.class)
public class ItemNotaFiscalBCTest {

    @Inject
	private ItemNotaFiscalBC itemNotaFiscalBC;
	
	@Before
	public void before() {
		for (ItemNotaFiscal itemNotaFiscal : itemNotaFiscalBC.findAll()) {
			itemNotaFiscalBC.delete(itemNotaFiscal.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		ItemNotaFiscal itemNotaFiscal = new ItemNotaFiscal(Integer.valueOf(1),"cfop","unidade",0D,null,null,null,null,null);
		itemNotaFiscalBC.insert(itemNotaFiscal);
		List<ItemNotaFiscal> listOfItemNotaFiscal = itemNotaFiscalBC.findAll();
		assertNotNull(listOfItemNotaFiscal);
		assertEquals(1, listOfItemNotaFiscal.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		ItemNotaFiscal itemNotaFiscal = new ItemNotaFiscal(Integer.valueOf(1),"cfop","unidade",0D,null,null,null,null,null);
		itemNotaFiscalBC.insert(itemNotaFiscal);
		
		List<ItemNotaFiscal> listOfItemNotaFiscal = itemNotaFiscalBC.findAll();
		assertNotNull(listOfItemNotaFiscal);
		assertEquals(1, listOfItemNotaFiscal.size());
		
		itemNotaFiscalBC.delete(itemNotaFiscal.getId());
		listOfItemNotaFiscal = itemNotaFiscalBC.findAll();
		assertEquals(0, listOfItemNotaFiscal.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		ItemNotaFiscal itemNotaFiscal = new ItemNotaFiscal(Integer.valueOf(1),"cfop","unidade",0D,null,null,null,null,null);
		itemNotaFiscalBC.insert(itemNotaFiscal);
		
		List<ItemNotaFiscal> listOfItemNotaFiscal = itemNotaFiscalBC.findAll();
		ItemNotaFiscal itemNotaFiscal2 = (ItemNotaFiscal)listOfItemNotaFiscal.get(0);
		assertNotNull(listOfItemNotaFiscal);

		// alterar para tratar uma propriedade existente na Entidade ItemNotaFiscal
		itemNotaFiscal2.setCfop("novo valor");
		itemNotaFiscalBC.update(itemNotaFiscal2);
		
		listOfItemNotaFiscal = itemNotaFiscalBC.findAll();
		ItemNotaFiscal itemNotaFiscal3 = (ItemNotaFiscal)listOfItemNotaFiscal.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade ItemNotaFiscal
		assertEquals("novo valor", itemNotaFiscal3.getCfop());
	}

}