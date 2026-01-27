package br.com.dbug.questlab.repository.projection;

import java.time.LocalDate;
//localizar os intens
public interface ProvasConcursoProjection {
    String getNomeConcurso();
    Integer getAno();
    String getNomeProva();
    String getCargoAssociado();
    LocalDate getDataAplicacao();
}