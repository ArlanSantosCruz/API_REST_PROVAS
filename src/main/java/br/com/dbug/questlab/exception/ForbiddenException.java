package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando o usuário não tem permissão para acessar um recurso.
 * HTTP Status: 403 FORBIDDEN
 */
public class ForbiddenException extends ApiException {

    private static final String ERROR_CODE = "FORBIDDEN";
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public ForbiddenException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para erros de autorização
    public static ForbiddenException acessoNegado() {
        return new ForbiddenException("Acesso negado. Permissões insuficientes");
    }

    public static ForbiddenException perfilInsuficiente(String perfilRequerido) {
        return new ForbiddenException(
                String.format("Acesso restrito ao perfil: %s", perfilRequerido)
        );
    }

    public static ForbiddenException recursoPrivado() {
        return new ForbiddenException("Você não tem permissão para acessar este recurso");
    }

    public static ForbiddenException operacaoRestrita() {
        return new ForbiddenException("Operação restrita a administradores");
    }
}