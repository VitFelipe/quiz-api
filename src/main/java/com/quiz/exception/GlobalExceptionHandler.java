package com.quiz.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final String MENSAGEM_DEFAULT = "Desculpe, um erro interno aconteceu. Tente nobvamente mais tarde ou entre em contato com o administrador.";

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<ApiError> handleBussinessExceptions(BussinessException ex) {
        ApiError apiError = new ApiError(
                new Date(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });

        ApiError apiError = new ApiError(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                errors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                List.of(ex.getMessage())
        );


        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object>  handleBadCredentialsException(UnauthorizedException ex, WebRequest request) {
        log.error("Exception caught", ex);
        ApiError apiError = new ApiError(
                new Date(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                List.of(ex.getMessage())
        );

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), getStatusCode(HttpStatus.UNAUTHORIZED), request);
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object>  handleForbiddenException(ForbiddenException ex, WebRequest request) {
        log.error("Exception caught", ex);
        ApiError apiError = new ApiError(
                new Date(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                List.of(ex.getMessage())
        );

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), getStatusCode(HttpStatus.FORBIDDEN), request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.error("Exception caught", ex);

        if (body instanceof String) {
            ApiError apiError = buildApiError(ex, statusCode, (String) body).build();
            return ResponseEntity.status(statusCode).body(apiError);
        }

        if (body instanceof ApiError) {
            return ResponseEntity.status(statusCode).body(body);
        }
        ApiError apiError = buildApiError(ex, statusCode, MENSAGEM_DEFAULT).build();
        return ResponseEntity.status(statusCode).body(apiError);
    }


    private ApiError.ApiErrorBuilder buildApiError(Exception ex, HttpStatusCode statusCode, String defaultMessage) {
        return ApiError.builder()
                .timestamp(new Date())
                .status(statusCode.value())
                .message(defaultMessage)
                .errors(List.of(ex.getMessage()));
    }


    private HttpStatusCode getStatusCode(HttpStatus status) {
        int code = 500;
        if (status == null) {
            return HttpStatusCode.valueOf(code);
        }
        return HttpStatusCode.valueOf(status.value());
    }

}
