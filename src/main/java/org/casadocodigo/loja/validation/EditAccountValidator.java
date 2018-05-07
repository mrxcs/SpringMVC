package org.casadocodigo.loja.validation;

import org.casadocodigo.loja.daos.UserDAO;
import org.casadocodigo.loja.models.AUser;
import org.springframework.validation.BindingResult;

public class EditAccountValidator {
	
	private AUser output;
		
	public EditAccountValidator(AUser n, BindingResult bR, UserDAO DAO) {
		
		AUser dbUser = DAO.loadUserByUsername(n.getLogin());
		
		if(!(bR.hasFieldErrors("name"))) {
			dbUser.setName(n.getName());
		}
				
		if(!(n.getAuthorities() == null) || !(bR.hasFieldErrors("roles"))) {
			dbUser.setRoles(n.getRoles());
		}
				
		this.output = dbUser;  	
	}
	
	public AUser getOutput() {
		return this.output;
	}
	
}
