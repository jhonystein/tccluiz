package edu.furb.sistemanfe.configuration;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(resource = "app")
public class AppConfig implements Serializable {
//teste de alteração.
	private static final long serialVersionUID = 1L;
	
	private String logoMaxFileSize = "10000";

	public String getLogoMaxFileSize() {
		return logoMaxFileSize;
	}
	
	public Long getMaxFileSize() {
		return  Long.parseLong(logoMaxFileSize);
	}
}
