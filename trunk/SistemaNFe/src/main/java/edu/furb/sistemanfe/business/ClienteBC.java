package edu.furb.sistemanfe.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.persistence.ClienteDAO;
import edu.furb.sistemanfe.pojo.ClienteCurvaABC;
import edu.furb.sistemanfe.pojo.ClienteVendas;
import edu.furb.sistemanfe.rest.ClienteDTO;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

@BusinessController
public class ClienteBC extends DelegateCrud<Cliente, Long, ClienteDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private SistemaNFeCredentials credentials;

	/**
	 * Sobrescreve o metodo ALL para garantir que somente será carregado
	 * Clientes do Emitente atual.
	 */
	@Override
	public List<Cliente> findAll() {
		ClienteDTO dto = new ClienteDTO();
		dto.setEmitente(credentials.getUsuario().getEmitente());
		return getDelegate().pesquisar(dto);
	}
	
	public List<Cliente> buscaClientes(String sortField, SortOrder sortOrder){
		ClienteDTO dto = new ClienteDTO();
		dto.setEmitente(credentials.getUsuario().getEmitente());
		return getDelegate().buscaClientes(dto, sortField, sortOrder);
	}

	/**
	 * Obtem um crliante com base em seu Documento
	 * 
	 * @param documento
	 * @return
	 */
	public Cliente buscaPorDocumento(String documento) {
		ClienteDTO dto = new ClienteDTO();
		dto.setEmitente(credentials.getUsuario().getEmitente());
		dto.setDocumento(documento);
		List<Cliente> lst = getDelegate().pesquisar(dto);
		if ((lst == null) || (lst.size() == 0)) {
			return null;
		}
		return lst.get(0);
	}

	/**
	 * Adualiza o cadastro do cliente com base na informação importada da Nota;
	 * 
	 * @param nfRet
	 */
	public void atualizaCadastro(NotaFiscal nfRet) {
		Cliente cliente = buscaPorDocumento(nfRet.getClienteNotaFiscal()
				.getDocumento());
		if ((cliente == null) || (cliente.getId() == null) || (cliente.getId() == 0)) {
			cliente = new Cliente();
			cliente.setDocumento(nfRet.getClienteNotaFiscal().getDocumento());
			cliente.setEmitente(nfRet.getEmitente());
		}
		cliente.setEndereco(nfRet.getEndereco());
		cliente.setNome(nfRet.getClienteNotaFiscal().getNome());
		cliente.setInscricaoEstadual(nfRet.getClienteNotaFiscal()
				.getInscricaoEstadual());
		cliente.setFone(nfRet.getClienteNotaFiscal().getFone());
		if ((cliente.getId() == null) || (cliente.getId() == 0)) {
			insert(cliente);
		} else {
			update(cliente);
		}
	}
	
	public List<ClienteCurvaABC> getDadosCurvaABC() {
		List<ClienteCurvaABC> ret = getDelegate().clientesABC(credentials.getUsuario().getEmitente());
		/**
		 * Atribui a qualificação e calcula o consumo acumulado de cada item
		 */
		int qualificacao = 0;
		BigDecimal consumoAcumulado = new BigDecimal(0);
		for (ClienteCurvaABC clienteCurvaABC : ret) {
			qualificacao++;
			
			consumoAcumulado = consumoAcumulado.add(clienteCurvaABC.getValorTotal());
			clienteCurvaABC.setQualificacao(qualificacao);
			clienteCurvaABC.setValortTotalAcumulado(consumoAcumulado);			
		}
		long classificacaoA = Math.round(  ret.size() * 0.2D);
		long classificacaoB = Math.round(ret.size() * 0.3D);
		//long classificacaoC = Math.round(ret.size() * 0.5D);
		
		Double percentualAcumulado = 0.0;
		/**
		 * Atribui a classificacao e calcula o percentual acumulado
		 */
		for (ClienteCurvaABC clienteCurvaABC : ret) {
			if (clienteCurvaABC.getQualificacao() <= classificacaoA){
				clienteCurvaABC.setClassificacao("A");			
			}else if((clienteCurvaABC.getQualificacao() > classificacaoA ) && (clienteCurvaABC.getQualificacao() <= classificacaoB)){
				clienteCurvaABC.setClassificacao("B");
			}else {
				clienteCurvaABC.setClassificacao("C");
			}
			
			BigDecimal teste = clienteCurvaABC.getValortTotalAcumulado().divide(consumoAcumulado, 3);
			percentualAcumulado = teste.doubleValue();
			percentualAcumulado  = (percentualAcumulado ) * 100;			
			clienteCurvaABC.setPercentualAcumulado(percentualAcumulado);			
		}
		
		return ret;
	}
	
	
	public List<ClienteVendas> getClientesVendas(Date dataIni, Date dataFim) {
		List<ClienteVendas> ret = getDelegate().clientesVendas(credentials.getUsuario().getEmitente(), dataIni, dataFim);
		return ret;
	}
}
