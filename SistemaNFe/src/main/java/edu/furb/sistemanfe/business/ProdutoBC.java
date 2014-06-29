package edu.furb.sistemanfe.business;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.ItemNotaFiscal;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.domain.Produto;
import edu.furb.sistemanfe.persistence.ProdutoDAO;
import edu.furb.sistemanfe.pojo.ProdutoCurvaABC;
import edu.furb.sistemanfe.pojo.ProdutoVendas;
import edu.furb.sistemanfe.rest.ProdutoDTO;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@BusinessController
public class ProdutoBC extends DelegateCrud<Produto, Long, ProdutoDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private SistemaNFeCredentials credentials;
	@Inject 
	private NotaFiscalBC notaFiscalBC;

	/**
	 * Sobrescreve o metodo ALL para garantir que somente seram exibidos
	 * Produtos do Emitente atual;
	 */
	@Override
	public List<Produto> findAll() {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setEmitente(credentials.getUsuario().getEmitente());
		return getDelegate().pesquisar(dto);
	}	

	public List<Produto> buscaProdutos(String sortField, SortOrder sortOrder){
		ProdutoDTO dto = new ProdutoDTO();
		dto.setEmitente(credentials.getUsuario().getEmitente());
		return getDelegate().buscaProdutos(dto, sortField, sortOrder);
	}	

	/**
	 * Carraga um produto com base em seu Código
	 * @param codigo
	 * @return
	 */
	public Produto buscaPorCodigo(String codigo) {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setEmitente(credentials.getUsuario().getEmitente());
		dto.setCodigo(codigo);
		List<Produto> lst = getDelegate().pesquisar(dto);
		if ((lst == null) || (lst.size() == 0)) {
			return null;
		}
		return lst.get(0);
	}

	@Transactional
	/**
	 * Este metodo tem o objetivo de atualizar o cadastro de produtos com base nos dados de uma nota
	 * @param nf NotaFiscal com os itens
	 */
	public void atualizaCadastro(NotaFiscal nf) {
		for (ItemNotaFiscal item : nf.getItemNotaFiscal()) {
			Produto produto = buscaPorCodigo(item.getProduto().getCodigo());
			if (produto == null) {
				produto = new Produto();
				produto.setCodigo(item.getProduto().getCodigo());
				produto.setEmitente(nf.getEmitente());
			}

			produto.setNome(item.getProduto().getNome());
			if ((produto.getId() == null) || (produto.getId() == 0)) {
				insert(produto);
			} else {
				update(produto);
			}
		}

	}

	public List<ProdutoCurvaABC> getDadosCurvaABC(Date dataIni, Date dataFim) {
		List<ProdutoCurvaABC> ret = getDelegate().produtosABC(credentials.getUsuario().getEmitente(), dataIni, dataFim);
		/**
		 * Atribui a qualificação e calcula o consumo acumulado de cada item
		 */
		int qualificacao = 0;
		Double consumoAcumulado = 0.0;
		for (ProdutoCurvaABC produtoCurvaABC : ret) {
			qualificacao++;
			consumoAcumulado += produtoCurvaABC.getConsumo();
			produtoCurvaABC.setQualificacao(qualificacao);
			produtoCurvaABC.setConsumoAcumulado(consumoAcumulado);			
		}
		/*
		 * Obtem os limites por classificação
		 */
		float valorBase = Math.round (ret.size()*0.20);
		int qualificaA = Math.round (valorBase);
		valorBase = Math.round (ret.size()*0.30);
		int qualificaB = Math.round (valorBase);
		/**
		 * Calcula o percentual acumulado
		 */
		Double percentualAcumulado = 0.0;
		for (ProdutoCurvaABC produtoCurvaABC : ret) {
			percentualAcumulado = (produtoCurvaABC.getConsumoAcumulado() / consumoAcumulado) * 100;			
			produtoCurvaABC.setPercentualAcumulado(percentualAcumulado);
			if(produtoCurvaABC.getQualificacao() <= qualificaA){
				produtoCurvaABC.setClassificacao("A");
			}else if(produtoCurvaABC.getQualificacao() <= qualificaB){
				produtoCurvaABC.setClassificacao("B");
			}else{
				produtoCurvaABC.setClassificacao("C");
			}
		}	

		return ret;
	}

	public List<ProdutoVendas> getProdutosVendas(Date dataIni, Date dataFim) {
		List<ProdutoVendas> ret = getDelegate().produtosVendas(credentials.getUsuario().getEmitente(), dataIni, dataFim);
		return ret;
	}
}
