package com.hoaxify.models;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface UniqueUserName {

	String message() default "{hoaxify.constraints.username.UniqueUsername.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
