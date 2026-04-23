package com.events.api.infra;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.events.api.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    // 1. Trata especificamente o 404 (Recurso não encontrado)
    // CRITICAL: Certifique-se que o parâmetro é ResourceNotFoundException.class
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleResourceNotFound(ResourceNotFoundException exception) {
        log.warn("Recurso não encontrado: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDTO(exception.getMessage(), 404));
    }

    // 2. Trata erros de validação (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionDTO>> handleValidation(MethodArgumentNotValidException ex) {
        log.error("Erro de validação detectado em {} campos", ex.getBindingResult().getErrorCount());
        List<ExceptionDTO> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ExceptionDTO(error.getField() + ": " + error.getDefaultMessage(), 400))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    // 3. Trata erros de lógica em tempo de execução
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> handleRuntime(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(exception.getMessage(), 400));
    }

    // 4. Fallback para qualquer outro erro inesperado (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleGeneral(Exception exception) {
        log.error("ERRO CRÍTICO NÃO TRATADO: ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDTO("Erro interno do servidor", 500));
    }
}