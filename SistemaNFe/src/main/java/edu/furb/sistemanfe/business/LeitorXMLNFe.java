package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.ArquivoXML;
import edu.furb.sistemanfe.domain.ClienteNotaFiscal;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Endereco;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.ItemNotaFiscal;
import edu.furb.sistemanfe.domain.Municipio;
import edu.furb.sistemanfe.domain.NotaFiscal;
import edu.furb.sistemanfe.domain.Pais;
import edu.furb.sistemanfe.domain.ProdutoNotaFiscal;
import edu.furb.sistemanfe.exception.ValidationException;
import edu.furb.sistemanfe.security.SistemaNFeCredentials;

public class LeitorXMLNFe {

	@Inject
	private NotaFiscalBC notaFiscalBC;
	@Inject
	private MunicipioBC municipioBC;
	@Inject
	private EstadoBC estadoBC;
	@Inject
	private PaisBC paisBC;
	@Inject
	private ProdutoBC produtoBC;
	@Inject
	private ClienteBC clienteBC;
	@Inject
	private EmitenteBC emitenteBC;
	@Inject
	private SistemaNFeCredentials credencial;

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

	public NotaFiscal readXml(ArquivoXML arquivo) throws ValidationException {
		File outfile = new File(arquivo.getNome());
		return readXml(outfile);
	}

	public NotaFiscal readXml(String pathFile) throws ValidationException {
		File f = new File(pathFile);
		if (!f.exists()) {
			throw new ValidationException("Erro ao ler arquivo: Arquivo não localizado.");
		}
		return readXml(f);
	}

	@Transactional
	public NotaFiscal readXml(File f) throws ValidationException {
		NotaFiscal nfRet = null;

		if(credencial.getUsuario().getEmitente()==null){
			throw new ValidationException("Erro ao ler arquivo: Usuário não possui emitente associado");
		}

		// Criamos uma classe SAXBuilder que vai processar o XML
		SAXBuilder sb = new SAXBuilder();
		try {
			// Este documento agora possui toda a estrutura do arquivo.
			Document d = sb.build(f);

			Element root = d.getRootElement();

			System.out.println("Nome da root: " + root.getName());

			List<Element> elementsNFeProc = root.getChildren();

			System.out.println("Tamanho da lista: " + elementsNFeProc.size());

			// imprime o nome dos elements da root
			for (Element elementNFeProc : elementsNFeProc) {
				if (ehTag(elementNFeProc, "NFE")) {
					List<Element> elementsNFe = elementNFeProc.getChildren();
					// Achar a chave da NFE.
					for (Element elementNFeCapa : elementsNFe) {
						if (ehTag(elementNFeCapa, "INFNFE")) {
							String chaveNfe = elementNFeCapa.getAttribute("Id")
									.getValue();
							String versaoProt = elementNFeCapa.getAttribute(
									"versao").getValue();
							/**
							 * Se a nota já existe na base, deve remover.
							 */
							nfRet = notaFiscalBC.buscaChaveNfe(chaveNfe);
							if (nfRet != null) {
								notaFiscalBC.delete(nfRet.getId());
							}
							nfRet = new NotaFiscal();
							String CNPJ = "";
							// Obter o Emitente da nota;
							List<Element> elementsInfNFe = elementNFeCapa
									.getChildren();
							for (Element elementInfNFe : elementsInfNFe) {
								if (ehTag(elementInfNFe, "EMIT")) {
									List<Element> elementsEmit = elementInfNFe
											.getChildren();
									for (Element elementEmit : elementsEmit) {
										if (ehTag(elementEmit, "CNPJ")) {
											CNPJ = elementEmit.getValue();
											break;
										}
									}
									break;
								}
							}
							/**
							 * Validando Emitente
							 */
							Emitente emitente = emitenteBC.buscaDocumento(CNPJ);
							if (emitente == null) {
								throw new ValidationException("Erro ao ler arquivo: Emitente do XML foi localizado na base.");
							}
							if(!emitente.equals(credencial.getUsuario().getEmitente())){
								throw new ValidationException("Erro ao ler arquivo: Emitente do XML é diferente do emitente do usuário atual.");
							}						

							nfRet.setEmitente(emitente);
							/** 
							 * Guarda só a parte numérica da chave
							 */
							nfRet.setChaveNfe(chaveNfe.trim().toUpperCase()
									.replaceAll("NFE", ""));
							nfRet.setVersao(versaoProt);
							nfRet.setDataImportacao(Calendar.getInstance()
									.getTime());
							break;
						}
					}
					if (nfRet == null) {
						// TODO: deve gerar exception pq não achou a TAG;
						throw new ValidationException("Erro ao ler arquivo: Não foi localizada TAG INFNFE.");
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
											nfRet.setModelo(elementIde.getValue());
										}
										if (ehTag(elementIde, "NNF")) {
											nfRet.setNumero(elementIde.getValue());
										}
										if (ehTag(elementIde, "NATOP")) {
											nfRet.setNaturezaOperacao(elementIde
													.getValue());
										}
										if (ehTag(elementIde, "SERIE")) {
											nfRet.setSerie(elementIde.getValue());
										}
										if (ehTag(elementIde, "TPEMIS")) {
											nfRet.setTipoEmissao(elementIde
													.getValue());
										}
										if (ehTag(elementIde, "DEMI")) {
											DateFormat formatter = new SimpleDateFormat(
													"yyyy-MM-dd");
											Date date = (Date) formatter
													.parse(elementIde
															.getValue());
											nfRet.setDataEmissao(date);
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
												}else if (ehTag(elementEnderDest,
														"nro")) {
													endDest.setNumero(elementEnderDest
															.getValue());
												}else if (ehTag(elementEnderDest, "fone")) {
													endDest.setFone1(elementEnderDest.getValue());
												}else if (ehTag(elementEnderDest,
														"CEP")) {
													endDest.setCep(elementEnderDest
															.getValue());
												}else if (ehTag(elementEnderDest,
														"xBairro")) {
													endDest.setBairro(elementEnderDest
															.getValue());
												}else if (ehTag(elementEnderDest,
														"fone")) {
													dest.setFone(elementEnderDest
															.getValue());
												}else if (ehTag(elementEnderDest,
														"cMun")) {
													Municipio municipio = municipioBC
															.buscaCodigoIbge(elementEnderDest
																	.getValue());
													if (municipio == null) {
														municipio = cadastraMunicipio(elementsEnderDest);
													}
													endDest.setMunicipio(municipio);
												}else if (ehTag(elementEnderDest,
														"UF")) {
													Estado estado = estadoBC
															.buscaSigla(elementEnderDest
																	.getValue());
													endDest.setEstado(estado);
												}else if (ehTag(elementEnderDest, "cPais")) {
													Pais pais = paisBC
															.buscaCodigoBacen(elementEnderDest
																	.getValue());
													endDest.setPais(pais);
												}
											}
											nfRet.setEndereco(endDest);
										}
									}
									nfRet.setClienteNotaFiscal(dest);
								}
								// Tratando Item;
								if (ehTag(elementInfNFe, "det")) {
									// Lendo dados do Item
									String nItem = elementInfNFe.getAttribute(
											"nItem").getValue();

									ItemNotaFiscal itemNota = new ItemNotaFiscal();
									ProdutoNotaFiscal prod = new ProdutoNotaFiscal();
									itemNota.setProduto(prod);
									itemNota.setOrdem(Integer.parseInt(nItem));
									// itemNota.getProduto().setEmitente(emitente);

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
									nfRet.addItem(itemNota);
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
											nfRet.setValorTotalNota(new BigDecimal(
													valor));
										} else if (ehTag(elementoImpostoNF,
												"vTotTrib")) {
											Double valor = Double
													.parseDouble(elementoImpostoNF
															.getValue());
											nfRet.setValorTotalTributos(new BigDecimal(
													valor));
										}
									}
								}
							}
							//System.out.println(nfRet.toString());
							nfRet = notaFiscalBC.insert(nfRet);
//							/**
//							 * Chama o metodo atualizar cadastro de produto
//							 */
//							produtoBC.atualizaCadastro(nfRet);
//							/**
//							 * Chama o metodo atualizar cadastro de Cliente
//							 */
//							clienteBC.atualizaCadastro(nfRet);
						}
					}
				}
			}

			return nfRet;

		} catch (JDOMException e) {
			e.printStackTrace();
			throw new ValidationException("Erro ao ler arquivo: Arquivo com formato inválido."+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ValidationException("Erro ao ler arquivo: Arquivo com inválido."+e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ValidationException("Erro ao ler arquivo: Erro critico."+ex.getMessage());
		}
	}

	private Municipio cadastraMunicipio(List<Element> elementsEnderDest) {
		try {
			String sigla = null;
			// acha os dados do municipio;
			Municipio munRet = new Municipio();
			for (Element elementEnderDest : elementsEnderDest) {
				if (ehTag(elementEnderDest, "cMun")) {
					munRet.setCodigoIbge(elementEnderDest.getValue());
				} else if (ehTag(elementEnderDest, "xMun")) {
					munRet.setNome(elementEnderDest.getValue());
				} else if (ehTag(elementEnderDest, "UF")) {
					sigla = elementEnderDest.getValue();
				}
			}
			Estado estado = null;
			if (sigla != null) {
				estado = estadoBC.buscaSigla(sigla);
			}
			munRet.setEstado(estado);
			return municipioBC.insert(munRet);
		} catch (Exception e) {
			return null;
		}
		// return null;
		// //acha UF;
		// for (Element elementEnderDest : elementsEnderDest) {
		// if (ehTag(elementEnderDest, "UF")) {
		// sigla = elementEnderDest.getValue()
		// estado = estadoBC.buscaSigla(elementEnderDest.getValue());
		// }
		// }
	}

}
