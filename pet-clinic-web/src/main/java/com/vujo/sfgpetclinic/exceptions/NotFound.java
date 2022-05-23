package com.vujo.sfgpetclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException {
    public NotFound() {
        this("Entity not found.");
    }

    public NotFound(String entityName) {
        super(entityName + " not found.");
    }
}
