package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada para violações de regras de negócio.
 * HTTP Status: 400 BAD REQUEST
 */
public class BusinessException extends ApiException {

    private static final String ERROR_CODE = "BUSINESS_ERROR";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BusinessException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para erros de negócio comuns
    public static BusinessException questaoAnulada() {
        return new BusinessException("Operação não permitida em questão anulada");
    }

    public static BusinessException usuarioInativo() {
        return new BusinessException("Operação não permitida para usuário inativo");
    }

    public static BusinessException alternativaCorretaExistente() {
        return new BusinessException("Já existe uma alternativa correta para esta questão");
    }

    public static BusinessException perfilInvalido(String perfil) {
        return new BusinessException(String.format("Perfil inválido: %s. Perfis válidos: ADMIN, PROFESSOR, ALUNO", perfil));
    }

    public static BusinessException senhaIncorreta() {
        return new BusinessException("Senha atual incorreta");
    }

    public static BusinessException senhaFraca() {
        return new BusinessException("A senha deve ter no mínimo 6 caracteres");
    }

    public static BusinessException questaoSemAlternativaCorreta() {
        return new BusinessException("Uma questão deve ter exatamente uma alternativa correta");
    }

    public static BusinessException minimoAlternativas(int minimo) {
        return new BusinessException(String.format("Uma questão deve ter no mínimo %d alternativas", minimo));
    }
}