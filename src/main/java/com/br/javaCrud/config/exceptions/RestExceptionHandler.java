package com.br.javaCrud.config.exceptions;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.w3c.dom.events.EventException;

import jakarta.validation.ValidationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param exception Trata exceções relacionadas a eventos não encontrados.
     * @return Retorna uma resposta HTTP com status 404 (Not Found) e uma mensagem
     *         de erro personalizada.
     */
    @ExceptionHandler(EventException.class)
    private ResponseEntity<RestErrorMessage> eventNotFounfHandler(EventException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage(),
                "Not Found!.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
    }

    /**
     * @param exception Trata exceções relacionadas a erros de validação.
     * @return Retorna uma resposta HTTP com status 400 (Bad Request) e uma mensagem
     *         de erro personalizada.
     */
    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<RestErrorMessage> handleValidationException(ValidationException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage(),
                "Validation Error.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    /**
     * @param exception Trata exceções relacionadas a erros de entrada/saída.
     * @return Retorna uma resposta HTTP com status 500 (Internal Server Error) e
     *         uma mensagem de erro personalizada.
     */
    @ExceptionHandler(IOException.class)
    private ResponseEntity<RestErrorMessage> handleIOException(IOException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), "Socket input and output error.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    /**
     * @param exception Trata exceções relacionadas a erros inesperados em tempo de
     *                  execução.
     * @return Retorna uma resposta HTTP com status 500 (Internal Server Error) e
     *         uma mensagem de erro personalizada.
     */
    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<RestErrorMessage> handleRuntimeException(RuntimeException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), "Runtime Error.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    /**
     * @param exception Trata exceções relacionadas a interrupções de execução.
     * @return Retorna uma resposta HTTP com status 500 (Internal Server Error) e
     *         uma mensagem de erro personalizada.
     */
    @ExceptionHandler(InterruptedException.class)
    private ResponseEntity<RestErrorMessage> handleInterruptedException(InterruptedException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), "Competition Error.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    /**
     * @param exception Trata exceções relacionadas a erros de banco de dados.
     * @return Retorna uma resposta HTTP com status 500 (Internal Server Error) e
     *         uma mensagem de erro personalizada.
     */
    @ExceptionHandler(SQLException.class)
    private ResponseEntity<RestErrorMessage> handleSQLException(SQLException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), "Database Error.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }
}
