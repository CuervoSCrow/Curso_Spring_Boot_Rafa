package com.laboratorio.springboot20.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {

        super(message);
        log.error(message);
    }
}
