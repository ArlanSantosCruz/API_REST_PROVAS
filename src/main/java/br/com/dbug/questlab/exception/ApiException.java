package br.com.dbug.questlab.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Classe base para todas as exceções da aplicação.
 * Define um código de erro personalizado além da mensagem.
 */
@Getter
public abstract class ApiException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus httpStatus;

    public ApiException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public ApiException(String message, String errorCode, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    /**
     * Método utilitário para construir mensagens padronizadas
     */
    protected static String buildMessage(String resource, Object identifier) {
        return String.format("%s não encontrado(a) com identificador: %s", resource, identifier);
    }

    protected static String buildMessage(String resource, String field, Object value) {
        return String.format("%s com %s '%s' já existe", resource, field, value);
    }
}