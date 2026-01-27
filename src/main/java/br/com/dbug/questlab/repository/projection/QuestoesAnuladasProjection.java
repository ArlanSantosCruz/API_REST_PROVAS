package br.com.dbug.questlab.repository.projection;

import java.time.LocalDate;
//localizar os intens

public interface QuestoesAnuladasProjection {
    String getEnunciado();
    String getDisciplina();
    String getAssunto();
    LocalDate getDataAnulacao();
    String getComentarioProfessor();
}