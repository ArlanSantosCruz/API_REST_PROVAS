package br.com.dbug.questlab.repository.projection;

public interface DesempenhoDisciplinaProjection {
    String getDisciplina();
    Long getTotalTentativas();
    Long getTotalAcertos();
    Long getNumeroQuestoesAnuladas();
    // SEM média - será calculada no Service/DTO
}
