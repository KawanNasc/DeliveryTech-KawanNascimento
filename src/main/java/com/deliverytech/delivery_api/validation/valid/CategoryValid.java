package com.deliverytech.delivery_api.validation.valid;

import com.deliverytech.delivery_api.validation.validator.CategoryValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryValid {
    String message() default "Categoria deve ser uma das opções válidas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}