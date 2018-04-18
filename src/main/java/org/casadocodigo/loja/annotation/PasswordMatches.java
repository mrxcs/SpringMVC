package org.casadocodigo.loja.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.casadocodigo.loja.validation.PasswordMatchesValidator;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.RetentionPolicy;

@Target({TYPE,ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
	String message() default "Senhas não conferem";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
