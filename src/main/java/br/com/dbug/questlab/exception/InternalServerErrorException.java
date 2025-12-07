package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada para erros internos do servidor.
 * HTTP Status: 500 INTERNAL SERVER ERROR
 */
public class InternalServerErrorException extends ApiException {

    private static final String ERROR_CODE = "INTERNAL_ERROR";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerErrorException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para erros internos
    public static InternalServerErrorException erroBancoDados(String operacao) {
        return new InternalServerErrorException(
                String.format("Erro ao %s no banco de dados", operacao)
        );
    }

    public static InternalServerErrorException erroIntegracao(String servico) {
        return new InternalServerErrorException(
                String.format("Erro na integração com o serviço: %s", servico)
        );
    }

    public static InternalServerErrorException erroProcessamento() {
        return new InternalServerErrorException("Erro no processamento da requisição");
    }
}