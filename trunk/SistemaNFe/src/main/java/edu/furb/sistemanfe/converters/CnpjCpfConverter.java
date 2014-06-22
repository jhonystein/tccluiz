package edu.furb.sistemanfe.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value= "CnpjCpfConverter")
public class CnpjCpfConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value){
		/*
		 * Irá converter CPF formatado para um sem pontos e traço. Ex.:
		 * 355.245.198-87 torna-se 35524519887. Irá converter CNPJ formatado
		 * para um sem pontos, traço e barra. Ex.: 07.374.998/0001-33 torna-se
		 * 07374998000133.
		 */
		String cnpjCpf = value;
		if (value != null && !value.equals(""))
			cnpjCpf = value.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("/", "");			 

		return cnpjCpf;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		/*
		 * Irá converter CPF não formatado para um com pontos e traço. Ex.:
		 * 35524519887 torna-se 355.245.198-87.
		 * 
		 * Irá converter CNPJ não formatado para um com pontos, traço e barra.
		 * Ex.: 07374998000133 torna-se 07.374.998/0001-33.
		 */
		String cnpjCpf = (String) value;
		if (cnpjCpf != null && cnpjCpf.length() == 11) {
			cnpjCpf = cnpjCpf.substring(0, 3) + "." + cnpjCpf.substring(3, 6) + "."
					+ cnpjCpf.substring(6, 9) + "-" + cnpjCpf.substring(9, 11);
		} else if (cnpjCpf != null && cnpjCpf.length() == 14) {
			cnpjCpf = cnpjCpf.substring(0, 2) + "." + cnpjCpf.substring(2, 5) + "."
					+ cnpjCpf.substring(5, 8) + "/" + cnpjCpf.substring(8, 12) + "-"
					+ cnpjCpf.substring(12, 14);
		}

		return cnpjCpf;
	}

}
