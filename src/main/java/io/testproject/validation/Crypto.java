package io.testproject.validation;

import java.lang.annotation.ElementType;import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value= {ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CryptoValidator.class)
public @interface Crypto {
	
	String message() default "Cryptocurrency name is not correct";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
