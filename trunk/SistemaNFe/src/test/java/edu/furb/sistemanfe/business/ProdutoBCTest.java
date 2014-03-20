

package edu.furb.sistemanfe.business;

import static org.junit.Assert.*;
import java.util.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.business.ProdutoBC;

@RunWith(DemoiselleRunner.class)
public class ProdutoBCTest {

    @Inject
	private ProdutoBC produtoBC;
	
	@Before
	public void before() {
		for (Produto produto : produtoBC.findAll()) {
			produtoBC.delete(produto.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Produto produto = new Produto("codigo","nome",null);
		produtoBC.insert(produto);
		List<Produto> listOfProduto = produtoBC.findAll();
		assertNotNull(listOfProduto);
		assertEquals(1, listOfProduto.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Produto produto = new Produto("codigo","nome",null);
		produtoBC.insert(produto);
		
		List<Produto> listOfProduto = produtoBC.findAll();
		assertNotNull(listOfProduto);
		assertEquals(1, listOfProduto.size());
		
		produtoBC.delete(produto.getId());
		listOfProduto = produtoBC.findAll();
		assertEquals(0, listOfProduto.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Produto produto = new Produto("codigo","nome",null);
		produtoBC.insert(produto);
		
		List<Produto> listOfProduto = produtoBC.findAll();
		Produto produto2 = (Produto)listOfProduto.get(0);
		assertNotNull(listOfProduto);

		// alterar para tratar uma propriedade existente na Entidade Produto
		// produto2.setUmaPropriedade("novo valor");
		produtoBC.update(produto2);
		
		listOfProduto = produtoBC.findAll();
		Produto produto3 = (Produto)listOfProduto.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Produto
		// assertEquals("novo valor", produto3.getUmaPropriedade());
	}

}