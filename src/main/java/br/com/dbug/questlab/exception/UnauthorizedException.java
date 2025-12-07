package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando a autenticação falha.
 * HTTP Status: 401 UNAUTHORIZED
 */
public class UnauthorizedException extends ApiException {

    private static final String ERROR_CODE = "UNAUTHORIZED";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para erros de autenticação
    public static UnauthorizedException credenciaisInvalidas() {
        return new UnauthorizedException("Credenciais inválidas");
    }

    public static UnauthorizedException tokenInvalido() {
        return new UnauthorizedException("Token de autenticação inválido ou expirado");
    }

    public static UnauthorizedException tokenNaoInformado() {
        return new UnauthorizedException("Token de autenticação não informado");
    }

    public static UnauthorizedException sessaoExpirada() {
        return new UnauthorizedException("Sessão expirada. Faça login novamente");
    }
}