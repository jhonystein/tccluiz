
package edu.furb.sistemanfe.business;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.persistence.NotaFiscalDAO;
import edu.furb.sistemanfe.rest.NotaFiscalDTO;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@BusinessController
public class NotaFiscalBC extends DelegateCrud<NotaFiscal, Long, NotaFiscalDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SistemaNFeCredentials credentials;
	
	/**
	 * Busca a nota pela chave da Nfe
	 * @param chaveNfe
	 * @return
	 */
	public NotaFiscal buscaChaveNfe(String chaveNfe) {
		NotaFiscalDTO dto = new NotaFiscalDTO();
		/**
		 * Sempre adiciona o filtro de emitente.
		 */
		dto.setEmitente(credentials.getUsuario().getEmitente());
		dto.setChaveNfe(chaveNfe);
		List<NotaFiscal> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}
	
	/**
	 * Metodo findAll sobrescrito para garantir acesso a somente notas do emitente atual
	 */
	@Override
	public List<NotaFiscal> findAll() {
		NotaFiscalDTO dto = new NotaFiscalDTO();
		dto.setEmitente(credentials.getUsuario().getEmitente());
		return getDelegate().pesquisar(dto);
	}

	public List<NotaFiscal> findByEmitente(Emitente emitente) {
		NotaFiscalDTO dto = new NotaFiscalDTO();
		dto.setEmitente(emitente);
		return getDelegate().pesquisar(dto);
	}
	
	/**
	 * Copnta o nro de notas de um emitente
	 * @param emitente
	 * @return
	 */
	public long contaNotasPorEmitente(Emitente emitente) {
		NotaFiscalDTO dto = new NotaFiscalDTO();
		dto.setEmitente(emitente);
		List<NotaFiscal> ret = getDelegate().pesquisar(dto);
		return (ret==null)?(-1):(ret.size());
	}
}
