package com.deliverytech.delivery_api.validation.validator;

import com.deliverytech.delivery_api.validation.valid.CategoryValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<CategoryValid, String> {

    private static final List<String> VALID_CATEGORIES = Arrays.asList(
        "Brasileira", "Italiana", "Japonesa", "Chinesa", "Mexicana",
        "Fast Food", "Pizza", "Hambúrguer", "Saudável", "Vegetariana",
        "Vegana", "Doces", "Bebidas", "Lanches", "Açaí"
    );

    @Override
    public void initialize(CategoryValid constraintAnnotation) {
        // Inicialização se necessária
    }

    @Override
    public boolean isValid(String category, ConstraintValidatorContext context) {
        if (category == null || category.trim().isEmpty()) {
            return false;
        }

        // Verifica se tem 10 ou 11 dígitos
        return VALID_CATEGORIES.contains(category);
    }
}