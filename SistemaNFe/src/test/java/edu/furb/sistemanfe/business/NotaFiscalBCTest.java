

package edu.furb.sistemanfe.business;

import static org.junit.Assert.*;
import java.util.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.business.NotaFiscalBC;

@RunWith(DemoiselleRunner.class)
public class NotaFiscalBCTest {

    @Inject
	private NotaFiscalBC notaFiscalBC;
	
	@Before
	public void before() {
		for (NotaFiscal notaFiscal : notaFiscalBC.findAll()) {
			notaFiscalBC.delete(notaFiscal.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		NotaFiscal notaFiscal = new NotaFiscal("chaveNfe","naturezaOperacao","modelo","serie","numero",new Date(),"tipoEmissao",null,null,null,null);
		notaFiscalBC.insert(notaFiscal);
		List<NotaFiscal> listOfNotaFiscal = notaFiscalBC.findAll();
		assertNotNull(listOfNotaFiscal);
		assertEquals(1, listOfNotaFiscal.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		NotaFiscal notaFiscal = new NotaFiscal("chaveNfe","naturezaOperacao","modelo","serie","numero",new Date(),"tipoEmissao",null,null,null,null);
		notaFiscalBC.insert(notaFiscal);
		
		List<NotaFiscal> listOfNotaFiscal = notaFiscalBC.findAll();
		assertNotNull(listOfNotaFiscal);
		assertEquals(1, listOfNotaFiscal.size());
		
		notaFiscalBC.delete(notaFiscal.getId());
		listOfNotaFiscal = notaFiscalBC.findAll();
		assertEquals(0, listOfNotaFiscal.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		NotaFiscal notaFiscal = new NotaFiscal("chaveNfe","naturezaOperacao","modelo","serie","numero",new Date(),"tipoEmissao",null,null,null,null);
		notaFiscalBC.insert(notaFiscal);
		
		List<NotaFiscal> listOfNotaFiscal = notaFiscalBC.findAll();
		NotaFiscal notaFiscal2 = (NotaFiscal)listOfNotaFiscal.get(0);
		assertNotNull(listOfNotaFiscal);

		// alterar para tratar uma propriedade existente na Entidade NotaFiscal
		// notaFiscal2.setUmaPropriedade("novo valor");
		notaFiscalBC.update(notaFiscal2);
		
		listOfNotaFiscal = notaFiscalBC.findAll();
		NotaFiscal notaFiscal3 = (NotaFiscal)listOfNotaFiscal.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade NotaFiscal
		// assertEquals("novo valor", notaFiscal3.getUmaPropriedade());
	}

}