package org.casadocodigo.loja.validation;

import org.casadocodigo.loja.daos.UserDAO;
import org.casadocodigo.loja.models.User;
import org.springframework.validation.BindingResult;

public class EditAccountValidator {
	
	private User output;
		
	public EditAccountValidator(User n, BindingResult bR, UserDAO DAO) {
		
		User dbUser = DAO.loadUserByUsername(n.getLogin());
		
		if(!(bR.hasFieldErrors("name"))) {
			dbUser.setName(n.getName());
		}
				
		if(!(n.getAuthorities() == null) || !(bR.hasFieldErrors("roles"))) {
			dbUser.setRoles(n.getRoles());
		}
				
		this.output = dbUser;  	
	}
	
	public User getOutput() {
		return this.output;
	}
	
}
