package com.deliverytech.delivery_api.validation.valid;

import com.deliverytech.delivery_api.validation.validator.CEPValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CEPValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CEPValid {
    String message() default "CEP deve ter formato válido (00000-0000 ou 000000000)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}