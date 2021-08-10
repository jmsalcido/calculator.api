package dev.calculator.model.v1.network;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
public class ApiError {

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
    }

    private HttpStatus status;
    private String message;
    private List<String> errors;
}
