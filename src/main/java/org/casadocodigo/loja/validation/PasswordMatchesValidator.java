package org.casadocodigo.loja.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.casadocodigo.loja.annotation.PasswordMatches;
import org.casadocodigo.loja.models.AUser;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
	
	@Override
    public void initialize(PasswordMatches constraintAnnotation) {       
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){   
        AUser user = (AUser) obj;
        return user.getPassword().equals(user.getMatchingPassword());    
    } 
}
