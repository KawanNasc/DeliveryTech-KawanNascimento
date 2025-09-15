package com.deliverytech.delivery_api.exception;

public class ConflictException extends RuntimeException {
    private final String conflictField;
    private final Object conflictValue;
    private final String errorCode;

    public ConflictException(String message, String conflictField, Object conflictValue, String errorCode) {
        super(message);
        this.conflictField = conflictField;
        this.conflictValue = conflictValue;
        this.errorCode = errorCode;
    }

    public String getConflictField() {
        return conflictField;
    }

    public Object getConflictValue() {
        return conflictValue;
    }

    public String getErrorCode() {
        return errorCode;
    }
}