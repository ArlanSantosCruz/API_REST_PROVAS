package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada para erros de validação de dados.
 * HTTP Status: 422 UNPROCESSABLE ENTITY
 */
public class ValidationException extends ApiException {

    private static final String ERROR_CODE = "VALIDATION_ERROR";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    public ValidationException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para validações específicas
    public static ValidationException campoObrigatorio(String campo) {
        return new ValidationException(String.format("O campo '%s' é obrigatório", campo));
    }

    public static ValidationException tamanhoCampo(String campo, int min, int max) {
        return new ValidationException(
                String.format("O campo '%s' deve ter entre %d e %d caracteres", campo, min, max)
        );
    }

    public static ValidationException emailInvalido(String email) {
        return new ValidationException(String.format("Email inválido: %s", email));
    }

    public static ValidationException cpfInvalido(String cpf) {
        return new ValidationException(String.format("CPF inválido: %s", cpf));
    }

    public static ValidationException cnpjInvalido(String cnpj) {
        return new ValidationException(String.format("CNPJ inválido: %s", cnpj));
    }

    public static ValidationException dataInvalida(String campo) {
        return new ValidationException(String.format("Data inválida para o campo '%s'", campo));
    }

    public static ValidationException dataFutura(String campo) {
        return new ValidationException(String.format("O campo '%s' não pode ser uma data futura", campo));
    }
}