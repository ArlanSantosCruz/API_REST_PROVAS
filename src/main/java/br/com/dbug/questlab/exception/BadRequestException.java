package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada para requisições malformadas.
 * HTTP Status: 400 BAD REQUEST
 */
public class BadRequestException extends ApiException {

    private static final String ERROR_CODE = "BAD_REQUEST";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para erros de requisição
    public static BadRequestException parametroInvalido(String parametro) {
        return new BadRequestException(String.format("Parâmetro inválido: %s", parametro));
    }

    public static BadRequestException parametroObrigatorio(String parametro) {
        return new BadRequestException(String.format("Parâmetro obrigatório não informado: %s", parametro));
    }

    public static BadRequestException formatoInvalido(String campo, String formato) {
        return new BadRequestException(
                String.format("Formato inválido para o campo '%s'. Formato esperado: %s", campo, formato)
        );
    }

    public static BadRequestException idInvalido() {
        return new BadRequestException("ID inválido ou malformado");
    }
}