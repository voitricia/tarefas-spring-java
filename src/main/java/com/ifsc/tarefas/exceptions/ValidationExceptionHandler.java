package com.ifsc.tarefas.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController // escuta as chamadas que chegam na api
public class ValidationExceptionHandler {

    // erro 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            error:"Bad Request",
            message:"Erro de validação de campos",
            request.getDescription(false),
            errors);

        return ResponseEntity.badRequest().body(apiError);
    }

    // erro500
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInternalServerError(Exception ex, WebRequest request) {

        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error:"Internal Server Error",
            message:"Erro no servidor",
            request.getDescription(false))

        return ResponseEntity.internalServerError().body(apiError);

}
}
