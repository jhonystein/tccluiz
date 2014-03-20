
package edu.furb.sistemanfe.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import edu.furb.sistemanfe.domain.*;
import java.util.*;
import javax.faces.model.*;
import edu.furb.sistemanfe.persistence.ClienteDAO;

// To remove unused imports press: Ctrl+Shift+o

@BusinessController
public class ClienteBC extends DelegateCrud<Cliente, Long, ClienteDAO> {
	private static final long serialVersionUID = 1L;
	
	
}
