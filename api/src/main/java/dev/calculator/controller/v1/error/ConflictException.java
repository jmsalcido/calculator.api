package dev.calculator.controller.v1.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="There was a conflict while processing request.")
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
