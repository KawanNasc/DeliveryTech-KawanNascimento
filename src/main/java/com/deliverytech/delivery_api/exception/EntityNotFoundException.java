package com.deliverytech.delivery_api.exception;

public class EntityNotFoundException extends RuntimeException {
    private String entityName;
    private Object entityId;
    private String errorCode;

    public EntityNotFoundException(String entityName, Object entityId) {
        super(String.format("%s com ID %s não foi encontrado(a)", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
        this.errorCode = "ENTITY_NOT_FOUND";
    }

    public EntityNotFoundException(String message) {
        super(message);
        this.errorCode = "ENTITY_NOT_FOUND";
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getEntityName() { return entityName; }
    public Object getEntityId() { return entityId; }
    public String getErrorCode() { return errorCode; }
}