package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import br.gov.frameworkdemoiselle.transaction.Transactional;

import edu.furb.sistemanfe.domain.Cliente;
import edu.furb.sistemanfe.domain.Endereco;
import edu.furb.sistemanfe.domain.NotaFiscal;

public class LeitorXMLNFe {

	@Inject
	private NotaFiscalBC notaFiscalBC;
	@Inject
	private ClienteBC clienteBC;

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

	@Transactional
	public List<NotaFiscal> readXml(String pathFile) {
		List<NotaFiscal> ret = new ArrayList<NotaFiscal>();
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
							String chaveNfe = elementNFe.getAttribute("Id")
									.getValue();
							nf.setChaveNfe(chaveNfe.trim().toUpperCase()
									.replaceAll("NFE", ""));

							List<Element> elementsInfNFe = elementNFe
									.getChildren();
							for (Element elementInfNFe : elementsInfNFe) {
								if (elementInfNFe.getName().trim()
										.toUpperCase().equals("IDE")) {
									List<Element> elementsIde = elementInfNFe
											.getChildren();
									for (Element elementIde : elementsIde) {
										if (elementIde.getName().trim()
												.toUpperCase().equals("MOD")) {
											nf.setModelo(elementIde.getValue());
										}
										if (elementIde.getName().trim()
												.toUpperCase().equals("NNF")) {
											nf.setNumero(elementIde.getValue());
										}
										if (elementIde.getName().trim()
												.toUpperCase().equals("NATOP")) {
											nf.setNaturezaOperacao(elementIde
													.getValue());
										}
										if (elementIde.getName().trim()
												.toUpperCase().equals("SERIE")) {
											nf.setSerie(elementIde.getValue());
										}
										if (elementIde.getName().trim()
												.toUpperCase().equals("TPEMIS")) {
											nf.setTipoEmissao(elementIde
													.getValue());
										}
										if (elementIde.getName().trim()
												.toUpperCase().equals("DEMI")) {
											DateFormat formatter = new SimpleDateFormat(
													"yyyy-MM-dd");
											Date date = (Date) formatter
													.parse(elementIde
															.getValue());
											nf.setDataEmissao(date);
										}
									}
								}
								if (elementInfNFe.getName().trim()
										.toUpperCase().equals("DEST")) {
									List<Element> elementsDest = elementInfNFe
											.getChildren();
									Cliente dest = nf.getCliente();
									// Se a nota não tiver cliente
									if (dest == null) {
										// Busca pela tag do Documento;
										for (Element elementDest : elementsDest) {
											if ((elementDest.getName().trim()
													.toUpperCase()
													.equals("CNPJ"))
													|| (elementDest.getName()
															.trim()
															.toUpperCase()
															.equals("CPF"))) {
												String documento = elementDest
														.getValue();
												// Busca o cliente na base, de
												// aconrdo com o documento;
												dest = clienteBC
														.buscaPorDocumento(documento);
												if (dest == null) {
													dest = new Cliente();
												}
												dest.setDocumento(documento);
												break;
											}
										}

									}
									// Alimenta os demais atributos do Cliente
									for (Element elementDest : elementsDest) {
										if (elementDest.getName().trim()
												.toUpperCase()
												.equals("xNome".toUpperCase())) {
											dest.setNome(elementDest.getValue());
										}
										if (elementDest.getName().trim()
												.toUpperCase()
												.equals("IE".toUpperCase())) {
											dest.setInscricaoEstadual(elementDest
													.getValue());
										}
										// Tratanedo o endereço.
										if (elementDest
												.getName()
												.trim()
												.toUpperCase()
												.equals("enderDest"
														.toUpperCase())) {
											Endereco endDest = dest
													.getEndereco();
											if (endDest == null) {
												endDest = new Endereco();
											}
											List<Element> elementsEnderDest = elementDest
													.getChildren();
											for (Element elementEnderDest : elementsEnderDest) {
												if (elementEnderDest
														.getName()
														.trim()
														.toUpperCase()
														.equals("xLgr"
																.toUpperCase())) {
													endDest.setLogradouro(elementEnderDest
															.getValue());
												}
												// TODO: Tratar os demais
												// atributos;
											}
											dest.setEndereco(endDest);
										}
									}
									nf.setCliente(dest);
								}
							}
							nf.setValorTotalNota(new BigDecimal(0D));
							nf.setValorTotalTributos(new BigDecimal(0D));

							System.out.println(nf.toString());
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
