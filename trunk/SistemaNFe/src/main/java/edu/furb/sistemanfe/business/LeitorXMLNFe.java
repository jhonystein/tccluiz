package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import edu.furb.sistemanfe.domain.NotaFiscal;

public class LeitorXMLNFe {

	@Inject
	private NotaFiscalBC notaFiscalBC;

	public class Ide {

	}

	public class Emit {

	}

	public class Dest {

	}

	public class Det {

	}

	interface LeitorNota {

		Ide obterIde();

		Emit obterEmit();

		Dest obterDest();

		List<Det> obterItens();
	}

	public class LeitorNota2 implements LeitorNota {
		Element tagNfe;

		public LeitorNota2(Element tagNfe) {
			tagNfe = tagNfe;
		}

		@Override
		public Ide obterIde() {
			Ide ret = new Ide();

			return null;
		}

		@Override
		public Emit obterEmit() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Dest obterDest() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Det> obterItens() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public List<NotaFiscal> readXml(String pathFile) {
		List<NotaFiscal> ret = new ArrayList<NotaFiscal>();
		// Aqui você informa o nome do arquivo XML.
		File f = new File(pathFile);
		if (!f.exists()) {
			return ret;
		}

		// Criamos uma classe SAXBuilder que vai processar o XML
		SAXBuilder sb = new SAXBuilder();
		try {
			// Este documento agora possui toda a estrutura do arquivo.
			Document d = sb.build(f);

			// Recuperamos o elemento root
			Element nfeProc = d.getRootElement();
			// o arquivo da nfe possui um name space então é necessario um
			// objeto para representa-lo
			Namespace ns = Namespace
					.getNamespace("http://www.portalfiscal.inf.br/nfe");

			// String versaoProt = nfeProc.getAttribute("versao",
			// ns).getValue();
			//
			// if(versaoProt == "2.00"){
			//
			// }

			Element root = d.getRootElement();
			Element nfeElement = null;

			System.out.println("Nome da root: " + root.getName());

			List<Element> elementsNFeProc = root.getChildren();

			System.out.println("Tamanho da lista: " + elementsNFeProc.size());

			// imprime o nome dos elements da root
			for (Element elementNFeProc : elementsNFeProc) {
				System.out.println("Filho de root nome:"
						+ elementNFeProc.getName());
				if (elementNFeProc.getName().trim().toUpperCase().equals("NFE")) {
					NotaFiscal nf = new NotaFiscal();
					List<Element> elementsNFe = elementNFeProc.getChildren();
					for (Element elementNFe : elementsNFe) {
						if (elementNFe.getName().trim().toUpperCase()
								.equals("INFNFE")) {
							String chaveNfe = elementNFe.getAttribute("Id").getValue();
							nf.setChaveNfe(chaveNfe.trim().toUpperCase().replaceAll("NFE", ""));
							
							List<Element> elementsInfNFe = elementNFe
									.getChildren();
							for (Element elementInfNFe : elementsInfNFe) {
								if(elementInfNFe.getName().trim().toUpperCase().equals("IDE")){
									List<Element> elementsIde = elementInfNFe
											.getChildren();
									for (Element elementIde : elementsIde) {
										if(elementIde.getName().trim().toUpperCase().equals("MOD")){
											nf.setModelo(elementIde.getValue());
										}
										if(elementIde.getName().trim().toUpperCase().equals("NNF")){
											nf.setNumero(elementIde.getValue() );
										}
										if(elementIde.getName().trim().toUpperCase().equals("NATOP")){
											nf.setNaturezaOperacao(elementIde.getValue());											
										}
										if(elementIde.getName().trim().toUpperCase().equals("SERIE")){
											nf.setSerie(elementIde.getValue());
										}
										if(elementIde.getName().trim().toUpperCase().equals("TPEMIS")){
											nf.setTipoEmissao(elementIde.getValue());
										}
										if(elementIde.getName().trim().toUpperCase().equals("DEMI")){
											DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
											Date date = (Date) formatter.parse(elementIde.getValue());
											nf.setDataEmissao(date);
										}										
									}
								}
							}
							nf.setValorTotalNota(new BigDecimal(0D));
							nf.setValorTotalTributos(new BigDecimal(0D));
							
							nf = notaFiscalBC.insert(nf);
							ret.add(nf);
						}
					}
	
				}
			}

			return ret;

		} catch (JDOMException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
