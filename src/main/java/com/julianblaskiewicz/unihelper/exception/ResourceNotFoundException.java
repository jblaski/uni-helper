package com.julianblaskiewicz.unihelper.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String parameter, String value, String resourceType) {
        super(resourceType + " with " + parameter + " of " + value + " not found");
    }
}
