package br.com.dbug.questlab.repository.projection;

public interface EvolucaoAlunoProjection {
    String getDisciplina();
    String getAssunto();
    Long getTotalTentativas();
    Long getTotalAcertos();
    // SEM percentual - será calculado no DTO
}

