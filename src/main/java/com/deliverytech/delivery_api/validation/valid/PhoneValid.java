package com.deliverytech.delivery_api.validation.valid;

import com.deliverytech.delivery_api.validation.validator.PhoneValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneValid {
    String message() default "Telefone deve ter formato válido (10 ou 11 dígitos)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}