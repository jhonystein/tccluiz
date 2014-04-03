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
import edu.furb.sistemanfe.domain.ClienteNotaFiscal;
import edu.furb.sistemanfe.domain.Endereco;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.ItemNotaFiscal;
import edu.furb.sistemanfe.domain.Municipio;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.domain.Produto;

public class LeitorXMLNFe {

	@Inject
	private NotaFiscalBC notaFiscalBC;
	@Inject
	private ClienteBC clienteBC;
	@Inject
	private MunicipioBC municipioBC;
	@Inject
	private EstadoBC estadoBC;

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

	private boolean ehTag(Element elemento, String nome) {
		return elemento.getName().trim().toUpperCase()
				.equals(nome.toUpperCase());

	}

	private boolean ehTag(Element elemento, String[] nomes) {
		for (String nome : nomes) {
			if (elemento.getName().trim().toUpperCase()
					.equals(nome.toUpperCase())) {
				return true;
			}
		}
		return false;

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
									// Lendo dados da capa da nota.
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
								if (ehTag(elementInfNFe, "DEST")) {
									ClienteNotaFiscal dest = new ClienteNotaFiscal();
									// Lendo dados do Destinatario
									List<Element> elementsDest = elementInfNFe
											.getChildren();
									// Alimenta os demais atributos do Cliente
									for (Element elementDest : elementsDest) {
										if (ehTag(elementDest, new String[]{"CNPJ", "CPF"})) {											
											dest.setDocumento(elementDest
													.getValue());
										}
										if (ehTag(elementDest, "xNome")) {
											dest.setNome(elementDest.getValue());
										}
										if (ehTag(elementDest, "IE")) {
											dest.setInscricaoEstadual(elementDest
													.getValue());
										}
										// Tratanedo o endereço.
										if (ehTag(elementDest, "enderDest")) {
											Endereco endDest = new Endereco();

											List<Element> elementsEnderDest = elementDest
													.getChildren();
											for (Element elementEnderDest : elementsEnderDest) {
												if (ehTag(elementEnderDest,
														"xLgr")) {
													endDest.setLogradouro(elementEnderDest
															.getValue());
												}
												if (ehTag(elementEnderDest,
														"nro")) {
													endDest.setNumero(elementEnderDest
															.getValue());
												}
												if (ehTag(elementEnderDest,
														"CEP")) {
													endDest.setCep(elementEnderDest
															.getValue());
												}
												if (ehTag(elementEnderDest,
														"xBairro")) {
													endDest.setBairro(elementEnderDest
															.getValue());
												}
												if (ehTag(elementEnderDest,
														"cMun")) {
													Municipio municipio = municipioBC
															.buscaCodigoIbge(elementEnderDest
																	.getValue());
													endDest.setMunicipio(municipio);
												}
												if (ehTag(elementEnderDest,
														"UF")) {
													Estado estado = estadoBC
															.buscaSigla(elementEnderDest
																	.getValue());
													endDest.setEstado(estado);
													if (estado != null) {
														endDest.setPais(estado
																.getPais());
													}
												}
											}
											nf.setEndereco(endDest);
										}
									}
									nf.setClienteNotaFiscal(dest);
								}
								if (ehTag(elementInfNFe, "det")) {
									// Lendo dados do Item
									
									String nItem = elementInfNFe.getAttribute(
											"nItem").getValue();
									ItemNotaFiscal itemNota = new ItemNotaFiscal();
									Produto prod = new Produto();
									itemNota.setProduto(prod);
									itemNota.setOrdem(Integer.parseInt(nItem));
									
									List<Element> elementsItem = elementInfNFe
											.getChildren();
									for (Element elementItem : elementsItem) {
										if (ehTag(elementItem,"prod")) {
											List<Element> elementsProd = elementItem
													.getChildren();
											// Alimentando os demais atributos do item
											// da nota
											for (Element elementProd : elementsProd) {
												if (ehTag(elementProd,"CFOP")) {
													itemNota.setCfop(elementProd
															.getValue());
												}
												if (ehTag(elementProd, "qTrib")) {
													itemNota.setQuantidade(elementProd
															.getValue());
												}
												if (ehTag(elementProd, "cProd")) {
													itemNota.getProduto().setCodigo(elementProd
															.getValue());
												}
												if (ehTag(elementProd, "xProd")) {
													itemNota.getProduto().setNome(elementProd
															.getValue());
												}
											}
										}
									}
									
									
									nf.addItem(itemNota);
								}
								if (ehTag(elementInfNFe, "total")) {
									// Lendo dados do Total da Nota
									List<Element> elementsTotal = elementInfNFe
											.getChildren();
									List<Element> elementosImpostoNF = elementsTotal.get(0).getChildren();
									for (Element elementoImpostoNF : elementosImpostoNF) {
										if (ehTag(elementoImpostoNF, "vNF")) {
											Double valor = Double.parseDouble(elementoImpostoNF
													.getValue());
											nf.setValorTotalNota(new BigDecimal(valor));
										}
										//TODO: encontrar forma de alimentar o campo Tributos;
									}
								}
							}
							//nf.setValorTotalNota(new BigDecimal(0D));
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
