package dev.calculator.controller.v1;

import dev.calculator.controller.v1.error.ApplicationRuntimeException;
import dev.calculator.model.v1.network.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, //
                                                                  HttpHeaders headers, //
                                                                  HttpStatus status, //
                                                                  WebRequest request) //
    {
        ApiError apiError = new ApiError(status, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, //
            HttpHeaders headers, //
            HttpStatus status, //
            WebRequest request) //
    {
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, //
                                                                  HttpHeaders headers, //
                                                                  HttpStatus status,  //
                                                                  WebRequest request) //
    {
        ApiError apiError = getApiError(ex);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    private ApiError getApiError(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    }

    @SuppressWarnings("unused")
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, //
                                                                   WebRequest request) //
    {
        String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @SuppressWarnings("unused")
    @ExceptionHandler({ApplicationRuntimeException.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApplicationRuntimeException exception = (ApplicationRuntimeException) ex;
        ApiError apiError = new ApiError(exception.getHttpStatus(),
                ex.getLocalizedMessage(),
                "error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
