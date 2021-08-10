package dev.calculator.controller.v1.error;

import org.springframework.http.HttpStatus;

public class ApplicationRuntimeException extends RuntimeException {

    private HttpStatus httpStatus;

    public ApplicationRuntimeException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApplicationRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
