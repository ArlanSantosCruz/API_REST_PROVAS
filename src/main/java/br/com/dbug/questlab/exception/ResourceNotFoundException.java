package br.com.dbug.questlab.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um recurso não é encontrado.
 * HTTP Status: 404 NOT FOUND
 */
public class ResourceNotFoundException extends ApiException {

    private static final String ERROR_CODE = "RESOURCE_NOT_FOUND";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public ResourceNotFoundException(String resource, Object identifier) {
        super(buildMessage(resource, identifier), ERROR_CODE, HTTP_STATUS);
    }

    public ResourceNotFoundException(String resource, Object identifier, Throwable cause) {
        super(buildMessage(resource, identifier), ERROR_CODE, HTTP_STATUS, cause);
    }

    // Métodos factory para recursos específicos
    public static ResourceNotFoundException usuario(Integer id) {
        return new ResourceNotFoundException("Usuário", id);
    }

    public static ResourceNotFoundException usuarioByEmail(String email) {
        return new ResourceNotFoundException(String.format("Usuário com email '%s' não encontrado", email));
    }

    public static ResourceNotFoundException disciplina(Integer id) {
        return new ResourceNotFoundException("Disciplina", id);
    }

    public static ResourceNotFoundException assunto(Integer id) {
        return new ResourceNotFoundException("Assunto", id);
    }

    public static ResourceNotFoundException questao(Integer id) {
        return new ResourceNotFoundException("Questão", id);
    }

    public static ResourceNotFoundException alternativa(Integer id) {
        return new ResourceNotFoundException("Alternativa", id);
    }

    public static ResourceNotFoundException concurso(Integer id) {
        return new ResourceNotFoundException("Concurso", id);
    }

    public static ResourceNotFoundException prova(Integer id) {
        return new ResourceNotFoundException("Prova", id);
    }

    public static ResourceNotFoundException banca(Integer id) {
        return new ResourceNotFoundException("Banca", id);
    }

    public static ResourceNotFoundException instituicao(Integer id) {
        return new ResourceNotFoundException("Instituição", id);
    }

    public static ResourceNotFoundException cargo(Integer id) {
        return new ResourceNotFoundException("Cargo", id);
    }
}
