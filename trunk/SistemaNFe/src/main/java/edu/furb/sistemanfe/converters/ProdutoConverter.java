package edu.furb.sistemanfe.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import br.gov.frameworkdemoiselle.util.Beans;
import edu.furb.sistemanfe.persistence.ProdutoDAO;
import edu.furb.sistemanfe.domain.Produto;

@FacesConverter(value= "ConversorProduto")
public class ProdutoConverter implements Converter {

	private ProdutoDAO produtoDAO = Beans.getReference(ProdutoDAO.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try{
		Object ret = null;
			if (component instanceof PickList) {
				Object dualList = ((PickList) component).getValue();
				DualListModel<?> dl = (DualListModel<?>) dualList;
				for (Object o : dl.getSource()) {
					//String id = String.valueOf(((Produto) o).getId());
					String id = String.valueOf(((Produto) o).getCodigo());
					if (value.equals(id)) {
						ret = o;
						break;
					}
				}
				if (ret == null)
					for (Object o : dl.getTarget()) {
						//String id = String.valueOf(((Produto) o).getId());
						String id = String.valueOf(((Produto) o).getCodigo());
						if (value.equals(id)) {
							ret = o;
							break;
						}
					}
			} else {
				if (value.trim().equals("")) {
					ret = null;
				} else {
					Long varId = Long.valueOf(value);
					ret =  produtoDAO.load(varId);
				}
			}
			return ret;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		try{
			if (value == null || value.equals("")) {
				return "";
			} else {			        
				//return String.valueOf(((Produto) value).getId());
				return String.valueOf(((Produto) value).getCodigo());
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}		
	}
}
