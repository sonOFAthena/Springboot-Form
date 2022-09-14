package com.artion.springboot.form.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = IdentificadorRegexValidador.class)
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD})
public @interface IdentificadorRegex {

    String message() default "Identificador inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
