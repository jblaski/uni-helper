package com.julianblaskiewicz.unihelper.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String id, String resourceType) {
        super(resourceType + " id not found : " + id);
    }
}
