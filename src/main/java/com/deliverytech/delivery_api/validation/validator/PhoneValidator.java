package com.deliverytech.delivery_api.validation.validator;

import com.deliverytech.delivery_api.validation.valid.PhoneValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneValid, String> {
    @Override
    public void initialize(PhoneValid constraintAnnotation) {
        // Inicialização se necessária
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        // Remove caracteres especiais e espaços
        String cleanPhone = phone.replaceAll("[^\\d]", "");

        // Verifica se tem 10 ou 11 dígitos
        return cleanPhone.length() == 10 || cleanPhone.length() == 11;
    }
}