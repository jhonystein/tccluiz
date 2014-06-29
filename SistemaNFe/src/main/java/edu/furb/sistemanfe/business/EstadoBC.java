package edu.furb.sistemanfe.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.Pais;
import edu.furb.sistemanfe.persistence.EstadoDAO;
import edu.furb.sistemanfe.rest.EstadoDTO;

@BusinessController
public class EstadoBC extends DelegateCrud<Estado, Long, EstadoDAO> {
	private static final long serialVersionUID = 1L;
	@Inject
	private ResourceBundle bundle;	
	@Inject
	private PaisBC paisBC;

	public Estado buscaSigla(String sigla) {
		EstadoDTO dto = new EstadoDTO();
		dto.setSigla(sigla);
		List<Estado> lst = getDelegate().pesquisar(dto);
		if ((lst == null) || (lst.size() == 0)) {
			return null;
		}
		return lst.get(0);
	}

	@Transactional
	public int inicializaListaEstados() {
		int linhas = 0;
		if (super.findAll().size() == 0) {
			System.out.println("INICILIZANDO ESTADOS");
			try {
				File f1 = new File(getClass().getClassLoader()
						.getResource("uf.csv").getPath());
				FileInputStream stream;
				stream = new FileInputStream(f1);

				String[] dados;
				String linha = null;

				Estado estado = null;
				Pais pais = paisBC.buscaCodigoBacen("55"); 

				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader br = new BufferedReader(reader);
				linha = br.readLine();
				while (linha != null) {
					dados = linha.split(";");

					String codIbge = dados[0];
					String ufSigla = dados[1];
					String nome = dados[2];

					estado = new Estado(ufSigla, nome, codIbge);
					estado.setPais(pais);
					super.insert(estado);
					linhas++;

					linha = br.readLine();
				}
				reader.close();
			} catch (FileNotFoundException e) {
				System.out
						.println("=============Arquivo não localizado.========");
				e.printStackTrace();
				return -2;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		return linhas;
	}

}
