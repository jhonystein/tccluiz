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
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.Municipio;
import edu.furb.sistemanfe.persistence.MunicipioDAO;
import edu.furb.sistemanfe.rest.MunicipioDTO;

@BusinessController
public class MunicipioBC extends DelegateCrud<Municipio, Long, MunicipioDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoBC estadoBC;

	public Municipio buscaCodigoIbge(String codigoIbge) {
		MunicipioDTO dto = new MunicipioDTO();
		dto.setCodigoIbge(codigoIbge);
		List<Municipio> lst = getDelegate().pesquisar(dto);
		if ((lst == null) || (lst.size() == 0)) {
			return null;
		}
		return lst.get(0);
	}

	@Transactional
	public int inicializaListaMunicipios() {
		int linhas = 0;
		try {
			if (super.findAll().size() == 0) {
				System.out.println("INICIALIZANDO MUNICIPIOS......");

				File f1 = new File(getClass().getClassLoader()
						.getResource("munic.csv").getPath());
				FileInputStream stream;
				stream = new FileInputStream(f1);

				String[] dados;
				String linha = null;

				Municipio mun = null;
				Estado estado = null;
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader br = new BufferedReader(reader);
				linha = br.readLine();
				while (linha != null) {
					dados = linha.split(";");

					String ufSigla = dados[0];
					String codIbge = dados[1];
					String nome = dados[2];

					estado = estadoBC.buscaSigla(ufSigla);
					mun = new Municipio(codIbge, nome, estado);
					super.insert(mun);

					linhas++;

					linha = br.readLine();
				}
				reader.close();
			}
			return linhas;
		} catch (FileNotFoundException e) {
			System.out.println("=============Arquivo não localizado.========");
			e.printStackTrace();
			return -2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

}
