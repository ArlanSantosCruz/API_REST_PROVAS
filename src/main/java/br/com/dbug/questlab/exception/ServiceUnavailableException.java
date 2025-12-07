package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um serviço necessário está indisponível.
 * HTTP Status: 503 SERVICE UNAVAILABLE
 */
public class ServiceUnavailableException extends ApiException {

    private static final String ERROR_CODE = "SERVICE_UNAVAILABLE";
    private static final HttpStatus HTTP_STATUS = HttpStatus.SERVICE_UNAVAILABLE;

    public ServiceUnavailableException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory
    public static ServiceUnavailableException servicoIndisponivel(String servico) {
        return new ServiceUnavailableException(
                String.format("Serviço %s temporariamente indisponível", servico)
        );
    }

    public static ServiceUnavailableException bancoDadosIndisponivel() {
        return new ServiceUnavailableException("Banco de dados temporariamente indisponível");
    }

    public static ServiceUnavailableException manutencao() {
        return new ServiceUnavailableException("Sistema em manutenção. Tente novamente mais tarde");
    }
}
