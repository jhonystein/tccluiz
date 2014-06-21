package edu.furb.sistemanfe.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.pojo.ClienteCurvaABC;
import edu.furb.sistemanfe.pojo.ClienteVendas;
import edu.furb.sistemanfe.rest.ClienteDTO;

@PersistenceController
public class ClienteDAO extends Crud<Cliente, Long, ClienteDTO> {

	private static final long serialVersionUID = 1L;

	public List<ClienteCurvaABC> clientesABC(Emitente emitente) {
		String sqlQuery = "SELECT "
				+ " new edu.furb.sistemanfe.pojo.ClienteCurvaABC(c.documento, c.nome, sum(n.valorTotalNota), count(c.id) "
				+ " ) "
				+ "  from NotaFiscal as n, Cliente as c "
				+ "  where c.documento = n.clienteNotaFiscal.documento and "
				+ " c.emitente = n.emitente and n.emitente = ?1 "
				+ "  group by c.documento, c.nome order by sum(n.valorTotalNota) desc ";
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, ClienteCurvaABC.class);
		query3.setParameter(1, emitente);
		List<ClienteCurvaABC> clientes = (List<ClienteCurvaABC>) query3
				.getResultList();
		System.out.println(clientes.toString());
		return clientes;

	}

	@Override
	protected List<Predicate> montaPredicadosConsulta(ClienteDTO dto,
			CriteriaBuilder builder, Root<Cliente> objeto) {
		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (dto.getId() != null) {
			Predicate p = builder.equal(objeto.<Long> get("id"), dto.getId());
			predicateList.add(p);
		}
		if (dto.getDocumento() != null) {
			Predicate p = builder.equal(objeto.<String> get("documento"),
					dto.getDocumento());
			predicateList.add(p);
		}
		if (dto.getNome() != null) {
			Predicate p = builder.equal(objeto.<String> get("nome"),
					dto.getNome());
			predicateList.add(p);
		}
		/**
		 * Sempre usa o critério de emitente atual na consulta
		 */
		Predicate p = builder.equal(objeto.<Emitente> get("emitente"),
				dto.getEmitente());
		predicateList.add(p);
		return predicateList;
	}

	public List<Cliente> buscaClientes(ClienteDTO dto, String sortField,
			SortOrder sortOrder) {
		TypedQuery<Cliente> query = montaQuery(dto, sortField, sortOrder);
		return this.paginacao(query);
	}

	public List<ClienteVendas> clientesVendas(Emitente emitente, Date dataIni,
			Date dataFim) {
		
		String sqlQuery = "SELECT "
				+ " new edu.furb.sistemanfe.pojo.ClienteVendas(c, "
				+ " count(c.id), sum(n.valorTotalNota)) "
				+ "  from NotaFiscal as n, Cliente as c "
				+ " where c.documento = n.clienteNotaFiscal.documento and "
				+ "c.emitente = n.emitente and n.emitente = ?1 and "
				+ " n.dataEmissao between ?2 and ?3 "
				+ "  group by c order by c.nome asc ";
		javax.persistence.Query query3 = getEntityManager().createQuery(
				sqlQuery, ClienteVendas.class);
		query3.setParameter(1, emitente);
		query3.setParameter(2, dataIni);
		query3.setParameter(3, dataFim);
		List<ClienteVendas> clientes = (List<ClienteVendas>) query3
				.getResultList();
		return clientes;
	}
}
