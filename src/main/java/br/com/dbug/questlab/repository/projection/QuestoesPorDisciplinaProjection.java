package br.com.dbug.questlab.repository.projection;
//localizar os intens

public interface QuestoesPorDisciplinaProjection {
    Integer getDisciplinaId();
    String getDisciplinaNome();
    Long getTotalQuestoes();
    Long getTotalQuestoesAnuladas();
}
