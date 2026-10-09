package com.yers4.gestion_tareas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler (RecursoDuplicadoException.class)
    public ResponseEntity<String> manejarrecursoDuplicado(RecursoDuplicadoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler (RecursoNoEncontradoException.class)
        public ResponseEntity<String> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    @ExceptionHandler (CredencialesInvalidadasException.class)
    public ResponseEntity<String> manejarCredencilaes(CredencialesInvalidadasException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
    }
    

