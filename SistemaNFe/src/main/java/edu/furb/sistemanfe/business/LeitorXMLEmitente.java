package edu.furb.sistemanfe.business;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.gov.frameworkdemoiselle.transaction.Transactional;
import edu.furb.sistemanfe.domain.Emitente;
import edu.furb.sistemanfe.domain.Endereco;
import edu.furb.sistemanfe.domain.Estado;
import edu.furb.sistemanfe.domain.Municipio;

/**
 * Esta classe tem o objetivo de ler do XML da nota o Emitente;
 * @author Luiz R. Marian
 *
 */
public class LeitorXMLEmitente {

	@Inject
	private MunicipioBC municipioBC;
	@Inject
	private EstadoBC estadoBC;

	private boolean ehTag(Element elemento, String nome) {
		return elemento.getName().trim().toUpperCase()
				.equals(nome.toUpperCase());

	}

	/**
	 * Este método tem o objetivo de ler o Emitente a partir de um arquivo XML de nota fiscal;
	 * @param f File de um arquivo XML.
	 * @return Objeto do tipo Emitente populado;
	 */
	public Emitente readXml(File f) {

		/**
		 * Validação
		 */
		if(!f.exists()){
			return null;
		}
		/**
		 * Inicializa o retorno como mulo
		 */
		Emitente ret = null;

		// Criamos uma classe SAXBuilder que vai processar o XML
		SAXBuilder sb = new SAXBuilder();
		try {
			// Este documento agora possui toda a estrutura do arquivo.
			Document d = sb.build(f);

			Element root = d.getRootElement();

			List<Element> elementsNFeProc = root.getChildren();

			/**
			 * Varre as tags para obter os dados necessário do Emitente;
			 */
			for (Element elementNFeProc : elementsNFeProc) {
				if (ehTag(elementNFeProc, "NFE")) {
					List<Element> elementsNFe = elementNFeProc.getChildren();
					for (Element elementNFeCapa : elementsNFe) {
						if (ehTag(elementNFeCapa, "INFNFE")) {							
							List<Element> elementsInfNFe = elementNFeCapa
									.getChildren();
							for (Element elementInfNFe : elementsInfNFe) {
								if (ehTag(elementInfNFe, "EMIT")) {
									ret = new Emitente();
									List<Element> elementsEmit = elementInfNFe
											.getChildren();
									for (Element elementEmit : elementsEmit) {
										if (ehTag(elementEmit, "CNPJ")) {
											ret.setDocumento(elementEmit.getValue());
											break;
										}else if (ehTag(elementEmit, "xnome")) {
											ret.setNome(elementEmit.getValue());
											break;
										}else if (ehTag(elementEmit, "IE")) {
											ret.setInscricaoEstadual(elementEmit.getValue());
											break;
										} else if (ehTag(elementEmit, "CRT")) {
											//TODO: Verifica necessidade futura para este campo
											break;
										} else if (ehTag(elementEmit, "enderEmit")) {
											List<Element> elementsEnderEmit = elementEmit
													.getChildren();
											Endereco endeEmi = new Endereco();
											for (Element elementEnderEmit : elementsEnderEmit) {
												if (ehTag(elementEnderEmit, "xLgr")) {
													endeEmi.setLogradouro(elementEnderEmit.getValue());
													break;
												}else if (ehTag(elementEnderEmit, "nro")) {
													endeEmi.setNumero(elementEnderEmit.getValue());
													break;
												}else if (ehTag(elementEnderEmit, "xBairro")) {
													endeEmi.setBairro(elementEnderEmit.getValue());
													break;
												}else if (ehTag(elementEnderEmit, "CEP")) {
													endeEmi.setCep(elementEnderEmit.getValue());
													break;
												}else if (ehTag(elementEnderEmit, "cMun")) {
													
													Municipio municipio = municipioBC
															.buscaCodigoIbge(elementEnderEmit
																	.getValue());
													if (municipio == null) {
														/**
														 * Alternativa para caso o mun ainda não estiver na base de dados. 
														 */
														municipio = cadastraMunicipio(elementsEnderEmit);
													}													
													endeEmi.setMunicipio(municipio);
													break;
												}else if (ehTag(elementEnderEmit, "UF")) {
													Estado estado = estadoBC
															.buscaSigla(elementEnderEmit
																	.getValue());
													endeEmi.setEstado(estado);
													if (estado != null) {
														endeEmi.setPais(estado
																.getPais());
													}
													
													break;
												}
											}//for
											ret.setEndereco(endeEmi);
											break;
										} 
									}//for
									break;//Cai fora depois de ler os campos do emitente
								}
							}//for
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

	/**
	 * Método com o objetivo de cadastrar um Municipio com base em valores de uma tag XML
	 * @param elementsEnderDest
	 * @return
	 */
	@Transactional
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
	}

}
