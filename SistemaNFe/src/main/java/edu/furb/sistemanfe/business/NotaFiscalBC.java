
package edu.furb.sistemanfe.business;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.persistence.NotaFiscalDAO;
import edu.furb.sistemanfe.rest.NotaFiscalDTO;

@BusinessController
public class NotaFiscalBC extends DelegateCrud<NotaFiscal, Long, NotaFiscalDAO> {
	private static final long serialVersionUID = 1L;
	
	public NotaFiscal buscaChaveNfe(String chaveNfe) {
		NotaFiscalDTO dto = new NotaFiscalDTO();
		dto.setChaveNfe(chaveNfe);
		List<NotaFiscal> lst = getDelegate().pesquisar(dto);
		if((lst == null) || (lst.size() == 0)){
			return null;
		}
		return lst.get(0);	
	}

	public List<NotaFiscal> findByEmitente(Emitente emitente) {
		NotaFiscalDTO dto = new NotaFiscalDTO();
		dto.setEmitente(emitente);
		return getDelegate().pesquisar(dto);
	}
	
	public long contaNotasPorEmitente(Emitente emitente) {
		NotaFiscalDTO dto = new NotaFiscalDTO();
		dto.setEmitente(emitente);
		List<NotaFiscal> ret = getDelegate().pesquisar(dto);
		return (ret==null)?(-1):(ret.size());
	}
}
