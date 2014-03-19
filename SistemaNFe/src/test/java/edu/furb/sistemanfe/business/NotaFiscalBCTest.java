package edu.furb.sistemanfe.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.NotaFiscal;

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
		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setChaveNfe("321654987032165498703216549870321654987011");
		notaFiscalBC.insert(notaFiscal);
		List<NotaFiscal> listaPais = notaFiscalBC.findAll();
		assertNotNull(listaPais);
		assertEquals(1, listaPais.size());
	}
	
	@Test
	public void deleteInsert() {
		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setChaveNfe("321654987032165498703216549870321654987011");
		notaFiscalBC.insert(notaFiscal);
		List<NotaFiscal> listaNotaFiscal = notaFiscalBC.findAll();
		assertNotNull("A lista não pode estar nula", listaNotaFiscal);
		assertEquals("Apos a inclusão, a lista deve conter 1 registro.", 1, listaNotaFiscal.size());
		notaFiscal = listaNotaFiscal.get(0);
		assertNotNull("Nota obtida para exclusão não pode ser null.", notaFiscal);
		notaFiscalBC.delete(notaFiscal.getId());
		assertEquals("Apos a exclusão, a lista deve estar vazia.", 0, listaNotaFiscal.size());
	}
}
