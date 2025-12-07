package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando há conflito de dados (duplicidade, etc.).
 * HTTP Status: 409 CONFLICT
 */
public class ConflictException extends ApiException {

    private static final String ERROR_CODE = "CONFLICT";
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public ConflictException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para conflitos comuns
    public static ConflictException emailDuplicado(String email) {
        return new ConflictException(buildMessage("Usuário", "email", email));
    }

    public static ConflictException cpfDuplicado(String cpf) {
        return new ConflictException(buildMessage("Usuário", "CPF", cpf));
    }

    public static ConflictException cnpjDuplicado(String cnpj, String recurso) {
        return new ConflictException(buildMessage(recurso, "CNPJ", cnpj));
    }

    public static ConflictException nomeDuplicado(String nome, String recurso) {
        return new ConflictException(buildMessage(recurso, "nome", nome));
    }

    public static ConflictException siglaDuplicada(String sigla, String recurso) {
        return new ConflictException(buildMessage(recurso, "sigla", sigla));
    }

    public static ConflictException assuntoDuplicado(String nome, Integer disciplinaId) {
        return new ConflictException(
                String.format("Assunto com nome '%s' já existe na disciplina ID: %d", nome, disciplinaId)
        );
    }

    public static ConflictException usuarioJaRespondeu(Integer usuarioId, Integer questaoId) {
        return new ConflictException(
                String.format("Usuário ID %d já respondeu a questão ID %d", usuarioId, questaoId)
        );
    }
}