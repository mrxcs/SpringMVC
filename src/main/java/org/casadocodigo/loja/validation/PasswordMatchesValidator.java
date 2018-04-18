package org.casadocodigo.loja.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.casadocodigo.loja.annotation.PasswordMatches;
import org.casadocodigo.loja.models.User;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
	
	@Override
    public void initialize(PasswordMatches constraintAnnotation) {       
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){   
        User user = (User) obj;
        return user.getPassword().equals(user.getMatchingPassword());    
    } 
}
