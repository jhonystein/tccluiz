package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.ClienteNotaFiscal;
import edu.furb.sistemanfe.domain.Emitente;
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
	private MunicipioBC municipioBC;
	@Inject
	private EstadoBC estadoBC;
	private Emitente emitente;

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

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	@Transactional
	public List<NotaFiscal> readXml(String pathFile) {

		// deve ter um emitente
		if (emitente == null) {
			return null;
		}

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
			// Element nfeProc = d.getRootElement();
			// o arquivo da nfe possui um name space então é necessario um
			// objeto para representa-lo
			// Namespace ns = Namespace
			// .getNamespace("http://www.portalfiscal.inf.br/nfe");

			Element root = d.getRootElement();
			// Element nfeElement = null;

			System.out.println("Nome da root: " + root.getName());

			List<Element> elementsNFeProc = root.getChildren();

			System.out.println("Tamanho da lista: " + elementsNFeProc.size());

			// imprime o nome dos elements da root
			for (Element elementNFeProc : elementsNFeProc) {
				if (ehTag(elementNFeProc, "NFE")) {
					NotaFiscal nf = null;
					List<Element> elementsNFe = elementNFeProc.getChildren();
					// Achar a chave da NFE.
					for (Element elementNFe : elementsNFe) {
						if (ehTag(elementNFe, "INFNFE")) {
							String chaveNfe = elementNFe.getAttribute("Id")
									.getValue();
							String versaoProt = elementNFe.getAttribute(
									"versao").getValue();
							// if(versaoProt == "2.00"){
							//
							// }
							nf = notaFiscalBC.buscaChaveNfe(chaveNfe);
							if (nf != null) {
								notaFiscalBC.delete(nf.getId());								
							}
							nf = new NotaFiscal();
							nf.setEmitente(emitente);
							// Guarda só a parte numérica
							nf.setChaveNfe(chaveNfe.trim().toUpperCase()
									.replaceAll("NFE", ""));
							nf.setVersao(versaoProt);
							nf.setDataImportacao(Calendar.getInstance()
									.getTime());
							break;
						}
					}
					if (nf == null) {
						// TODO: deve gerar exception pq não achou a TAG;
						return null;
					}
					// Trata os demais campos do XML;
					for (Element elementNFe : elementsNFe) {
						if (ehTag(elementNFe, "INFNFE")) {
							List<Element> elementsInfNFe = elementNFe
									.getChildren();
							for (Element elementInfNFe : elementsInfNFe) {
								if (ehTag(elementInfNFe, "IDE")) {
									// Lendo dados da capa da nota.
									List<Element> elementsIde = elementInfNFe
											.getChildren();
									for (Element elementIde : elementsIde) {
										if (ehTag(elementIde, "MOD")) {
											nf.setModelo(elementIde.getValue());
										}
										if (ehTag(elementIde, "NNF")) {
											nf.setNumero(elementIde.getValue());
										}
										if (ehTag(elementIde, "NATOP")) {
											nf.setNaturezaOperacao(elementIde
													.getValue());
										}
										if (ehTag(elementIde, "SERIE")) {
											nf.setSerie(elementIde.getValue());
										}
										if (ehTag(elementIde, "TPEMIS")) {
											nf.setTipoEmissao(elementIde
													.getValue());
										}
										if (ehTag(elementIde, "DEMI")) {
											DateFormat formatter = new SimpleDateFormat(
													"yyyy-MM-dd");
											Date date = (Date) formatter
													.parse(elementIde
															.getValue());
											nf.setDataEmissao(date);
										}
									}
								}
								// DADOS do Destinatário
								if (ehTag(elementInfNFe, "DEST")) {
									ClienteNotaFiscal dest = new ClienteNotaFiscal();
									// Lendo dados do Destinatario
									List<Element> elementsDest = elementInfNFe
											.getChildren();
									// Alimenta os demais atributos do Cliente
									for (Element elementDest : elementsDest) {
										if (ehTag(elementDest, new String[] {
												"CNPJ", "CPF" })) {
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
														"fone")) {
													dest.setFone(elementEnderDest
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
								// Tratando Item;
								if (ehTag(elementInfNFe, "det")) {
									// Lendo dados do Item
									String nItem = elementInfNFe.getAttribute(
											"nItem").getValue();
									
									ItemNotaFiscal itemNota = new ItemNotaFiscal();
									Produto prod = new Produto();
									itemNota.setProduto(prod);
									itemNota.setOrdem(Integer.parseInt(nItem));
									itemNota.getProduto().setEmitente(emitente);

									List<Element> elementsItem = elementInfNFe
											.getChildren();
									for (Element elementItem : elementsItem) {
										// Tratar atributos do produto;
										if (ehTag(elementItem, "prod")) {
											List<Element> elementsProd = elementItem
													.getChildren();
											// Alimentando os demais atributos
											// do item
											// da nota
											for (Element elementProd : elementsProd) {
												if (ehTag(elementProd, "CFOP")) {
													itemNota.setCfop(elementProd
															.getValue());
												}
												if (ehTag(elementProd, "qCom")) {
													Double valor = Double
															.parseDouble(elementProd
																	.getValue());
													itemNota.setQuantidade(valor);
												}
												if (ehTag(elementProd, "cProd")) {
													itemNota.getProduto()
															.setCodigo(
																	elementProd
																			.getValue());
												}
												if (ehTag(elementProd, "xProd")) {
													itemNota.getProduto()
															.setNome(
																	elementProd
																			.getValue());
												}
												if (ehTag(elementProd, "uCom")) {
													itemNota.setUnidade(elementProd
															.getValue());
												}
												if (ehTag(elementProd, "vUnCom")) {
													Double valor = Double
															.parseDouble(elementProd
																	.getValue());
													itemNota.setValorUnitario(new BigDecimal(
															valor));
												}
												if (ehTag(elementProd, "vProd")) {
													Double valor = Double
															.parseDouble(elementProd
																	.getValue());
													itemNota.setValorTotal(new BigDecimal(
															valor));
												}
											}
										}
										// Tratar atributos de impostos;
										if (ehTag(elementItem, "imposto")) {
											List<Element> elementsImposto = elementItem
													.getChildren();
											for (Element elementImposto : elementsImposto) {
												if (ehTag(elementImposto,
														"vTotTrib")) {
													Double valor = Double
															.parseDouble(elementImposto
																	.getValue());
													itemNota.setValorTotalTributos(new BigDecimal(
															valor));
												}
											}
										}
									}

									// Adiciona o Item a lista;
									nf.addItem(itemNota);
								}
								if (ehTag(elementInfNFe, "total")) {
									// Lendo dados do Total da Nota
									List<Element> elementsTotal = elementInfNFe
											.getChildren();
									List<Element> elementosImpostoNF = elementsTotal
											.get(0).getChildren();
									for (Element elementoImpostoNF : elementosImpostoNF) {
										if (ehTag(elementoImpostoNF, "vNF")) {
											Double valor = Double
													.parseDouble(elementoImpostoNF
															.getValue());
											nf.setValorTotalNota(new BigDecimal(
													valor));
										} else if (ehTag(elementoImpostoNF,
												"vTotTrib")) {
											Double valor = Double
													.parseDouble(elementoImpostoNF
															.getValue());
											nf.setValorTotalTributos(new BigDecimal(
													valor));
										}
									}
								}
							}
							// nf.setValorTotalNota(new BigDecimal(0D));
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
